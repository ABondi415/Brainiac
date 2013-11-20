package Browser;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adam
 */
public class ErrorWebPageException extends Exception{
    //ErrorWebPageException class.  
    String str;
    public ErrorWebPageException(String webpage){
        str = "Web page: "+webpage+" does not start with http://.  Adding http://";
    }
    public String getLocalizedMessage(){
        return str;
    }
    
}
