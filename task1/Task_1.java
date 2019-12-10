import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Task_1 extends DefaultHandler {

	public List<String> strs=new ArrayList<String>();
	/*Class defined Aes function encode and decode*/
	public Aes aes=new Aes();
	public String mode;
	public String encodeRule;

    public void startDocument() throws SAXException{
		super.startDocument();
    }

	public void setMode(String mode){
		this.mode = mode;
	}

	public void setEncodeRule(String encodeRule){
		this.encodeRule=encodeRule;
	}

    public void startElement(String uri,String localName,
            String qName,Attributes attrs)throws SAXException {
		String str_temp="";
		str_temp +="<"+qName;
        int len=attrs.getLength();
        for(int i=0;i<len;i++){
			str_temp +=" ";
			str_temp +=attrs.getQName(i);
			str_temp +="=\"";
			str_temp +=this.mode.equals("encode")?aes.AESEncode(this.encodeRule,attrs.getValue(i)):aes.AESDecode(this.encodeRule,attrs.getValue(i));
			str_temp +="\"";
        }
		str_temp +=">";
		strs.add(str_temp);
    }

    public void characters(char[] ch,int start,
            int length)throws SAXException {
		if(!new String(ch,start,length).trim().isEmpty()){
			String temp=this.mode.equals("encode")?aes.AESEncode(this.encodeRule,new String(ch,start,length).trim()):aes.AESDecode(this.encodeRule,new String(ch,start,length).trim());
			strs.add(temp);
		}
    }

    public void endElement(String uri,String localName,
            String qName)throws SAXException {
		strs.add("</"+qName+">");
    
    }

	public List<String> getStrings(){
		return strs;
	}

	/* write the result to xml file*/
	public void WriteToFile(List<String> Strings,String filename){
		try{
			File f_out=new File(filename);

			Iterator it = Strings.iterator();

			if(!f_out.exists()){
				f_out.createNewFile();
			}
			FileWriter fw = new FileWriter(f_out.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			for(int i=0;i<strs.size();i++){
				String temp = strs.get(i);
				if(!temp.trim().isEmpty()){
					bw.write(temp+"\n");
				}
			}
			bw.close();
		} catch (IOException e){
			e.printStackTrace();
		} 
	}

    public static void main(String[] args) {
		/*create sax parser*/
        SAXParserFactory sdf=SAXParserFactory.newInstance();
        SAXParser sp=null;
        try {
            sp=sdf.newSAXParser();

			Scanner scanner=new Scanner(System.in);
			System.out.print("Pelase input mode:");
			String mode=scanner.next();
			System.out.print("Pelase input encodeRule:");
			String encodeRule=scanner.next();
			System.out.print("Pelase input xml filename:");
			String filename_in = scanner.next();
			System.out.print("Pelase input filename for result:");
			String filename_out=scanner.next();
            
			File f_in=new File(filename_in);
			Task_1 xt=new Task_1();

			/*set the mode(encode or decode) and encodeRule*/
			xt.setMode(mode);
			xt.setEncodeRule(encodeRule);
			/*start parse xml file*/
            sp.parse(f_in,xt);
			List<String> strs = xt.getStrings();
			xt.WriteToFile(strs,filename_out);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
