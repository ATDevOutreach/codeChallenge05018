package com.fahamutech.af.controller;

import com.fahamutech.af.DAO.ReceiveMessageDao;
import com.fahamutech.af.DAO.SentMessageDao;
import com.fahamutech.af.afcore.AfricasTalkingGateway;
import com.fahamutech.af.database.Index;
import com.fahamutech.af.utils.Constants;
import com.fahamutech.af.utils.HibernateConfig;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    /**
     * this method used to send the message to users from the internet
     *
     * @param message the message we send
     * @param numbers the list of the number you send message to
     * @param key     key obtained after you sig up for this service to be implemented
     * @return json report of the message you send to the list of the numbers
     */
    @RequestMapping(value = "/sms/send", method = RequestMethod.GET)
    public String sendMessage(@RequestParam(value = "message") String message,
                              @RequestParam(value = "numbers") String numbers,
                              @RequestParam(value = "key", required = false, defaultValue = "1234") String key) {
        AfricasTalkingGateway talkingGateway
                = new AfricasTalkingGateway(Constants.DEBUG_USERNAME, Constants.API_KEY, Constants.DEBUG_MODE);
        JSONArray jsonArray = new JSONArray();
        new SentMessageDao().insertSentmessage(jsonArray);
        try {
            jsonArray = talkingGateway.sendMessage(numbers, message);
            return jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonArray.put("empty");
            return jsonArray.toString();
        }

    }

    /**
     * this method is called when we create a subscription to the number
     *  from the internet. To be implemented
     * @param phoneNumber the number we subscribed
     * @param shortCode short code we subscribe to
     * @param keyword the     topic we subscribe
     * @return the status of the subscription
     */
    @RequestMapping(value = "/sms/csub", method = RequestMethod.GET)
    public String createSubscription(@RequestParam(value = "phoneNumber") String phoneNumber,
                                     @RequestParam(value = "shortCode") String shortCode,
                                     @RequestParam(value = "keyword") String keyword) {

        AfricasTalkingGateway gateway = new AfricasTalkingGateway(Constants.DEBUG_USERNAME, Constants.API_KEY,
                Constants.DEBUG_MODE);
        JSONObject subscription = new JSONObject();
        try {
            subscription = gateway.createSubscription(phoneNumber, shortCode, keyword);
            return subscription.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return subscription.toString();
        }
    }

    /**
     * method fetch all premium subscribed message based on the topic.
     *To be implemented
     * @param shortCode a number you subcribed
     * @param keyword   the topic you want to fetch message
     * @return json contain all new sms
     */
    @RequestMapping(value = "/sms/premium/all", method = RequestMethod.GET)
    public String fetchPremiumSubscription(@RequestParam(value = "shortCode") String shortCode,
                                           @RequestParam(value = "keyword") String keyword) {
        AfricasTalkingGateway gateway = new AfricasTalkingGateway(Constants.DEBUG_USERNAME, Constants.API_KEY,
                Constants.DEBUG_MODE);
        JSONArray jsonArray = new JSONArray();
        int lastIndex = 0;
        try {

            do {
                jsonArray = gateway.fetchPremiumSubscriptions(shortCode, keyword, getPremiumIndex());
                lastIndex = jsonArray.getJSONObject((jsonArray.length() - 1)).getInt("id");
                //save the last id
                setPremiumLastIndex(lastIndex);

            } while (jsonArray.length() > 0);
            return jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return jsonArray.toString();
        }

    }

    /**
     * used to fetch all the new message
     *
     * @return all the new sms in json
     */
    @RequestMapping(value = "/sms/all")
    public String fetchAllMessage() {
        AfricasTalkingGateway gateway = new AfricasTalkingGateway(Constants.DEBUG_USERNAME, Constants.API_KEY,
                Constants.DEBUG_MODE);
        JSONArray jsonArray = new JSONArray();
        int lastIndex;
        try {

            jsonArray = gateway.fetchMessages(getMassageIndex());
            lastIndex = jsonArray.getJSONObject((jsonArray.length() - 1)).getInt("id");
            //save the last index to the database
            setMessageLastIndex(lastIndex);

            return jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return jsonArray.toString();
        }
    }


    /**
     * this method called when a message is sent from user to
     * the system
     *
     * @param from=number of the sender
     * @param to=mumber   where message goes
     * @param text=the    main message
     * @param date=       show when a message is received
     * @param id=the      unique number of the message
     * @param linkId .
     */
    @RequestMapping(value = "/sms/receive", method = RequestMethod.POST)
    public void receiveMessage(@RequestParam(value = "from") String from,
                               @RequestParam(value = "to") String to,
                               @RequestParam(value = "text") String text,
                               @RequestParam(value = "date") String date,
                               @RequestParam(value = "id") String id,
                               @RequestParam(value = "linkId", required = false, defaultValue = "N/A") String linkId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("from", from);
            jsonObject.put("to", to);
            jsonObject.put("date", date);
            jsonObject.put("id", id);
            jsonObject.put("linkId", linkId);
            jsonObject.put("text", text);

            //return jsonObject.toString();
            AfricasTalkingGateway gateway = new AfricasTalkingGateway(Constants.DEBUG_USERNAME, Constants.API_KEY,
                    Constants.DEBUG_MODE);
            gateway.sendMessage(from, " I am a lumberjack. I sleep all night and work all day!");

            //insert int the database
            new ReceiveMessageDao().insertReceivedMessage(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * this method is called when the message sent is delivered.
     * to be implemented
     *
     * @param status=whether           its success or not
     * @param phoneNumber=the          number we send the message
     * @param networkCode=the          operate which return the delivery report
     * @param id=the                   unique identification of the message
     * @param failureReason=decription if message is not received
     */
    @RequestMapping(value = "/sms/delivery", method = RequestMethod.POST)
    public void deliverReport(@RequestParam(value = "status") String status,
                              @RequestParam(value = "phoneNumber") String phoneNumber,
                              @RequestParam(value = "networkCode") String networkCode,
                              @RequestParam(value = "id") String id,
                              @RequestParam(value = "failureReason", required = false, defaultValue = "")
                                      String failureReason) {
        //update the database

    }

    /**
     * this method is called when the user subscribe to the service
     *. to be implemented
     * @param phoneNumber=the number of the user
     * @param shortCode=the   code he subscribe to
     * @param keyword=the     topic he/she subcribe to
     * @param updateType      if its Addition means he subscribe if Deletion means he is unsubscribe
     */
    @RequestMapping(value = "/sms/sub", method = RequestMethod.POST)
    public void subscriptionNotification(@RequestParam(value = "phoneNumber") String phoneNumber,
                                         @RequestParam(value = "shortCode") String shortCode,
                                         @RequestParam(value = "keyword") String keyword,
                                         @RequestParam(value = "updateType") String updateType) {
        //update notification


    }

    /**
     * this method is called when user opt out of the bulk sms or alphanumeric sms
     * to be implemented
     * @param senderId sender
     * @param phoneNumber=the number opt out
     */
    @RequestMapping(value = "/sms/unsub", method = RequestMethod.POST)
    public void bulkOptOut(@RequestParam(value = "senderId") String senderId,
                           @RequestParam(value = "phoneNumber") String phoneNumber) {
        //put data in the database and update the black list

    }


    //********************************************//
    // manage the last index of the fetch massage //
    //********************************************//

    /**
     * get the last downloaded message from the database
     *
     * @return the last id
     */
    private int getMassageIndex() {
        Transaction transaction = null;
        int lastId = 0;
        try (Session session = HibernateConfig.getSession()) {

            transaction = session.beginTransaction();
            List<Index> from_index_as_index = session.createQuery("from Index as index").list();
            for (Index index : from_index_as_index) {
                if (index.getName().equals(Constants.MESSAGE_LAST_INDEX)) lastId = index.getLastId();
            }
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
        return lastId;
    }

    /**
     * get the last downloaded premium message from the database
     *
     * @return the last id
     */
    private int getPremiumIndex() {
        Transaction transaction = null;
        int lastId = 0;
        try (Session session = HibernateConfig.getSession()) {

            transaction = session.beginTransaction();
            List<Index> from_index_as_index = session.createQuery("from Index as index").list();
            for (Index index : from_index_as_index) {
                if (index.getName().equals(Constants.PREMIUM_LAST_INDEX)) lastId = index.getLastId();
            }
            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
        return lastId;
    }

    /**
     * update the last id massage after fetch
     *
     * @param id the last id to update
     */
    private void setMessageLastIndex(int id) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();
//            Index index=new Index();
//            index.setLastId(id);
//            index.setName(Constants.MESSAGE_LAST_INDEX);
            //update the database
            session.createQuery("update Index ind set ind.lastId= :newId where ind.name= :oldId")
                    .setParameter("newId", id)
                    .setParameter("oldId", Constants.MESSAGE_LAST_INDEX)
                    .executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * update the premium last id fetched massage
     *
     * @param id the last id
     */
    private void setPremiumLastIndex(int id) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();
//            Index index=new Index();
//            index.setLastId(id);
//            index.setName(Constants.PREMIUM_LAST_INDEX);
            //update the database
            session.createQuery("update Index ind set ind.lastId= :newId where ind.name= :oldId")
                    .setParameter("newId", id)
                    .setParameter("oldId", Constants.PREMIUM_LAST_INDEX)
                    .executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

}
