/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Browser;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author A9712
 */
public class Browser {
    public Browser(JPanel parent){
        NativeInterface.open();  
        UIUtils.setPreferredLookAndFeel();
        parent.add(createContent(), BorderLayout.CENTER);
    }
    
    private static JComponent createContent() {
        JPanel contentPane = new JPanel(new BorderLayout());
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        final JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.navigate("http://www.google.com");
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        contentPane.add(webBrowserPanel, BorderLayout.CENTER);
        return contentPane;
    }
}