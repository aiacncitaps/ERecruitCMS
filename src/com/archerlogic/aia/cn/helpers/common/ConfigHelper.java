package com.archerlogic.aia.cn.helpers.common;

import com.quix.aia.cn.imo.data.properties.ConfigurationProperties;
import com.quix.aia.cn.imo.database.HibernateFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class ConfigHelper
{
    //* ------------------------------------------------------------------------
    private String _getConfig(String configKey)
    {
        String configValue = null;
        
        Session session = null;
        Transaction transaction = null;
        
        try
        {
            session = HibernateFactory.openSession();
            transaction = session.beginTransaction();
            
            ConfigurationProperties config = (ConfigurationProperties)session.createCriteria(ConfigurationProperties.class)
                    .add(Restrictions.eq("configurationKey", configKey))
                    .uniqueResult();
            
            if (config != null)
            {
                configValue = config.getConfigurationValue();
            }
            
            transaction.commit();
        }
        catch (Exception ex)
        {
            configValue = null;
            
            if (transaction != null)
            {
                transaction.rollback();
            }
            
            ex.printStackTrace();
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
        
        return configValue;
    }
    
    //* ------------------------------------------------------------------------
    public String getICalendarServiceUrl()
    {
        return _getConfig("iCalendarServiceUrl");
    }
}
