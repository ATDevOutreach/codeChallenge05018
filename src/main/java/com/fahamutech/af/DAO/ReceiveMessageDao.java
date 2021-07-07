package com.fahamutech.af.DAO;

import com.fahamutech.af.database.ReceivedMessage;
import com.fahamutech.af.utils.HibernateConfig;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * this class is response to save thw received text to database
 */
public class ReceiveMessageDao {

    /**
     * insert received sms to database
     * @param jsonObject json contain the data
     */
    public void insertReceivedMessage(JSONObject jsonObject){
        Transaction transaction=null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();
            ReceivedMessage receivedMessage = new ReceivedMessage(
                    jsonObject.getString("from"),
                    jsonObject.getString("to"),
                    jsonObject.getString("date"),
                    jsonObject.getString("id"),
                    jsonObject.getString("linkId")
            );
            session.save(receivedMessage);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
