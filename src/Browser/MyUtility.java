/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Browser;

/**
 *
 * @author A9712
 */
public class MyUtility {
        //A MyUtility class which throws an ErrorWebPageException.
    public void checkWebPage(String webpage) throws ErrorWebPageException{
        //If the webpage does not start with "http://" throw an exception.
        if(!webpage.startsWith("http://")){
            throw new ErrorWebPageException(webpage);
        }
    }
}
