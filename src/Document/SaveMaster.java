/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Document;

import java.io.File;
import javax.swing.JButton;

/**
 *
 * @author mzt5106
 */
public class SaveMaster extends JButton{
    SaveMasterClient smc; 
    
    public SaveMaster(SaveMasterClient smc){
        this.setText("Save Master");
        this.smc = smc;
    }
    
    public void writeFile(File file){
//        smc.sendFile(file);
    }
    
    public void getFile(){
        smc.getFile();
    }
}
