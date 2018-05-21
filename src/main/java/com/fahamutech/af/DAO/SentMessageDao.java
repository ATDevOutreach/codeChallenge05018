package com.fahamutech.af.DAO;

import com.fahamutech.af.database.SentMessage;
import com.fahamutech.af.utils.HibernateConfig;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * this classs manage the message sent to save to the database
 */
public class SentMessageDao {

    /**
     * insert new data to the database
     * @param jsonArray contain all messages sent information
     */
    public void insertSentmessage(JSONArray jsonArray) {
        Transaction transaction = null;

        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SentMessage sentMessage = new SentMessage(
                        jsonObject.getString("number"),
                        jsonObject.getString("cost"),
                        jsonObject.getString("messageId"),
                        jsonObject.getString("status")
                );
                session.save(sentMessage);
            }
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * select all sent message from the database
     * @return list of all the message stored
     */
    public List<SentMessage> getAllSentMessage() {
        Transaction transaction = null;
        List<SentMessage> list;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();
            list = (List<SentMessage>) session.createQuery("from SentMessage as s").list();
            return list;
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            return new ArrayList<>();
        }
    }
}
