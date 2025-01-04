
package blotterrecordmanagement;

/**
 *
 * @author Naps
 */
public class InputManager {
    public boolean isNumeric(String in){
        String input = in;
        
        boolean numeric = false;
        if(null!=in&&!in.equals("")){
            for(int i=0;i<input.length();i++){
                numeric = false;

                if(input.charAt(i)=='0'){
                    numeric = true;
                }else if(input.charAt(i)=='1'){
                    numeric = true;
                }else if(input.charAt(i)=='2'){
                    numeric = true;
                }else if(input.charAt(i)=='3'){
                    numeric = true;
                }else if(input.charAt(i)=='4'){
                    numeric = true;
                }else if(input.charAt(i)=='5'){
                    numeric = true;
                }else if(input.charAt(i)=='6'){
                    numeric = true;
                }else if(input.charAt(i)=='7'){
                    numeric = true;
                }else if(input.charAt(i)=='8'){
                    numeric = true;
                }else if(input.charAt(i)=='9'){
                    numeric = true;
                }else{
                    break;
                }
            }
        }
        
        return numeric;
    }
}
