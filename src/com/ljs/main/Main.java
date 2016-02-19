package com.ljs.main;

import java.applet.AppletContext;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;


import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JTextPane;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.net.URL;





import org.jsoup.Jsoup; //import Jsoup
import org.jsoup.nodes.Document; //import Jsoup
import org.jsoup.select.Elements;
import org.omg.CORBA.portable.ApplicationException;

import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; //import Jsoup

public class Main  {

	private JFrame frame;
	
	public static ArrayList<String> nicknamelist = new ArrayList<>();
	
	// 닉네임 모델
	public static DefaultListModel listModel;
	private JTextField tfUrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/**
		 *  프레임 셋팅
		 */
		frame = new JFrame();
		frame.setTitle("루리웹 마이피 추첨");
		frame.setBounds(100, 100, 450, 534);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
/*		
 		textField = new JTextField();
		textField.setBounds(114, 10, 308, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		-
		
		JLabel jlUrl = new JLabel("URL");
		jlUrl.setBounds(12, 13, 57, 15);
		frame.getContentPane().add(jlUrl);		
*/
		
		listModel = new DefaultListModel();
		
		
		//	추출버튼
		JButton btnExtract = new JButton("1.추출하기");
		btnExtract.setFont(new Font("굴림", Font.PLAIN, 12));
		
		// 스크롤 뷰 추가
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 76, 200, 396);
		frame.getContentPane().add(scrollPane);
		
		
		// Jlist 추가
		final JList list = new JList();
		list.setFont(new Font("굴림", Font.PLAIN, 12));
		scrollPane.setViewportView(list);
		
		
		btnExtract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nicknamelist.clear();
				listModel.clear();
				Connection(tfUrl.getText());				
				for(int i = 0; i<nicknamelist.size();i++){
					String name = nicknamelist.get(i);
					listModel.addElement(name);
				}
				list.setModel(listModel);
				
			}
		});
		btnExtract.setBounds(325, 41, 97, 23);
		frame.getContentPane().add(btnExtract);
		
		// URL 텍스트 필드
		tfUrl = new JTextField();
		tfUrl.setBounds(92, 10, 330, 21);
		frame.getContentPane().add(tfUrl);
		tfUrl.setColumns(10);
		
		// 라벨
		JLabel jlUrl = new JLabel("URL 입력창");
		jlUrl.setFont(new Font("굴림", Font.PLAIN, 12));
		jlUrl.setBounds(10, 13, 80, 15);
		frame.getContentPane().add(jlUrl);
		
		// 당첨자 뽑기 버튼
		JButton button = new JButton("2. 당첨자 뽑기");
		button.setFont(new Font("굴림", Font.PLAIN, 12));
		button.setBounds(243, 74, 156, 23);
		frame.getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RadomCatch();				
			}
		});
		
		JLabel label = new JLabel("루리웹 by 원킬투강냉이");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				openUrl("http://mypi.ruliweb.daum.net/mypi.htm?id=ung1212");
			}
		});
		label.setBounds(271, 471, 151, 15);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("추첨자 목록");
		label_1.setFont(new Font("굴림", Font.PLAIN, 12));
		label_1.setBounds(10, 51, 200, 15);
		frame.getContentPane().add(label_1);
		


		JTextPane txtpnKkl = new JTextPane();
		txtpnKkl.setEditable(false);
		String manual="1. 마이피 관리하기에 가서 전체숨김 항목을 '공개'로 변경하다 \n\n2.마이피에서 추첨할 게시물을 클릭하고 브라우저 창 위에 있는 url을 복사한다."
				+ " \n\n3. 복사한 URL을 URL 입력창에 붙여 놓은뒤 추출하기 버튼을 클릭한다. \n\n4.왼쪽에 해당 게시물에 있는 닉네임들이 출력된것을 확인 후 당첨자 뽑기를 클릭한다.";
		txtpnKkl.setText(manual);
		txtpnKkl.setBounds(222, 103, 200, 309);
		frame.getContentPane().add(txtpnKkl);
		
		
		setUIFont (new javax.swing.plaf.FontUIResource("굴림", Font.PLAIN, 15));
		
//		UIManager.put("JList.font",new Font("Gulim", Font.PLAIN, 12));

		
	}
	
	public boolean Connection(String url){
		try {
			System.out.println(url);
			  if(url == null || url.equals("")){				  
				  JOptionPane.showMessageDialog(null, "URL을 입력해주세요", "오류", JOptionPane.ERROR_MESSAGE);
				  return false;
			  }
			  Document doc = Jsoup.connect(url).get(); //웹에서 내용을 가져온다.
		      Elements contents01 = doc.select("td.mypiReply div.mypiZmenu b"); //내용 중에서 원하는 부분을 가져온다.
		      Elements contents02 = doc.select("td.mypiReply div.readcomment");
		      

		      for(int q=0; q<contents01.size();q++){
		    	  String nickname = contents01.get(q).text();
		    	  String comment = contents02.get(q).text();
		    	  nicknamelist.add(nickname);    	  
		      }	  
		      
		      if(nicknamelist.size() < 1){
				  JOptionPane.showMessageDialog(null, "마이피 공개여부를 확인하십시요", "오류", JOptionPane.ERROR_MESSAGE);
		      }else{	
		    	  // 중복제거
			      HashSet hs = new HashSet(nicknamelist);
			      nicknamelist = new ArrayList<>(hs);
		      }
		      
		} catch (Exception e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.   
			JOptionPane.showMessageDialog(null, "URL을 확인해주세요", "오류", JOptionPane.ERROR_MESSAGE);
		      e.printStackTrace();
		}
		return false;
	}
	/**
	 * 랜덤 뽑기
	 */
	public void RadomCatch(){
		if(nicknamelist.size() > 0){
			int i = (int) (Math.random() * nicknamelist.size());
//			System.out.println(i+"");
			JOptionPane.showMessageDialog(null, "'"+nicknamelist.get(i)+"'\n축하드립니다.","당첨 메세지", JOptionPane.INFORMATION_MESSAGE);
		}else{
			  JOptionPane.showMessageDialog(null, "추출하기를 먼저 해주십시요", "오류", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	/**
	 * UI 폰트 변경
	 * @param f
	 */
	public static void setUIFont(javax.swing.plaf.FontUIResource f) {
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	        Object key = keys.nextElement();
	        Object value = UIManager.get(key);
	        if (value instanceof javax.swing.plaf.FontUIResource)
	            UIManager.put(key, f);
	    }
	}
	
	public static void openUrl(String url) {
        String os = System.getProperty("os.name");
        Runtime runtime = Runtime.getRuntime();
        try {
            // Block for Windows Platform
            if (os.startsWith("Windows")) {
                String cmd = "rundll32 url.dll,FileProtocolHandler " + url;
                Process p = runtime.exec(cmd);
            }
            // Block for Mac OS
            else if (os.startsWith("Mac OS")) {
                Class fileMgr = Class.forName("com.apple.eio.FileManager");
                Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
                openURL.invoke(null, new Object[] { url });
            }
            // Block for UNIX Platform
            // else {
            // String[] browsers = {"firefox", "opera", "konqueror", "epiphany",
            // "mozilla", "netscape" };
            // String browser = null;
            // for (int count = 0; count < style="color: rgb(153, 0, 0);">length
            // && browser == null; count++)
            // if (runtime.exec(new String[] {"which",
            // browsers[count]}).waitFor() == 0)
            // browser = browsers[count];
            // if (browser == null)
            // throw new Exception("Could not find web browser");
            // else
            // runtime.exec(new String[] {browser, url});
            // }
        } catch (Exception x) {
            System.err.println("Exception occurd while invoking Browser!");
            x.printStackTrace();
        }
    }
}
