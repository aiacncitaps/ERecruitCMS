/*******************************************************************************
* -----------------------------------------------------------------------------
* <br>
* <p><b>Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.</b> 
* <br>
* <br>
* This SOURCE CODE FILE, which has been provided by Quix as part
* of a Quix Creations product for use ONLY by licensed users of the product,
* includes CONFIDENTIAL and PROPRIETARY information of Quix Creations.
* <br>
* USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
* OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
* THE PRODUCT.<br>
* <br>
* </p>
* -----------------------------------------------------------------------------
* <br>
* <br>
* Modification History:
* Date                       Developer           Description
* 5-January-2015             khyati             Added Copy rights
***************************************************************************** */

package com.quix.aia.cn.imo.utilities;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class MyPasswordAuthenticator extends Authenticator
{

    public MyPasswordAuthenticator(String username, String password)
    {
        user = username;
        pw = password;
    }

    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(user, pw);
    }

    String user;
    String pw;
}
