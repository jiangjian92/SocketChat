package client; 
import java.awt.BorderLayout; 
import java.awt.Frame; 
import java.awt.TextArea; 
import java.awt.TextField; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream; 
import java.io.IOException; 
import java.net.Socket; 
import java.net.UnknownHostException; 

public class ChatClient extends Frame { 
  // 用户输入区域 
  private TextField tfTxt = new TextField(); 
  // 内容展示区域 
  private TextArea tarea = new TextArea(); 
  private Socket socket = null; 
  // 数据输出流 
  private DataOutputStream dataOutputStream = null; 
  public static void main(String[] args) { 
    new ChatClient().launcFrame(); 
  } 
  /** 
   * 建立一个简单的图形化窗口 
   * 
   */
  public void launcFrame() { 
    setLocation(300, 200); 
    this.setSize(200, 400); 
    add(tfTxt, BorderLayout.SOUTH); 
    add(tarea, BorderLayout.NORTH); 
    pack(); 
    // 监听图形界面窗口的关闭事件 
    this.addWindowListener(new WindowAdapter() { 
      @Override
      public void windowClosing(WindowEvent e) { 
        System.exit(0); 
        disConnect(); 
      } 
    }); 
    tfTxt.addActionListener(new TFLister()); 
    setVisible(true); 
    connect(); 
  } 
  /** 
   * 连接服务器 
   */
  public void connect() { 
    try { 
      // 新建服务端连接 
      socket = new Socket("127.0.0.1", 8189); 
      // 获取客户端输出流 
      dataOutputStream = new DataOutputStream(socket.getOutputStream()); 
      System.out.println("连上服务端"); 
    } catch (UnknownHostException e) { 
      e.printStackTrace(); 
    } catch (IOException e) { 
      e.printStackTrace(); 
    } 
  } 
  /** 
   * 关闭客户端资源 
   * 
   */
  public void disConnect() { 
    try { 
      dataOutputStream.close(); 
      socket.close(); 
    } catch (IOException e) { 
      e.printStackTrace(); 
    } 
  } 
  /** 
   * 向服务端发送消息 
   * 
   */
  private void sendMessage(String text) { 
    try { 
      dataOutputStream.writeUTF(text); 
      dataOutputStream.flush(); 
    } catch (IOException e1) { 
      e1.printStackTrace(); 
    } 
  } 
  
  private void getMessage() throws IOException{
	  DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
	  String message = dataInputStream.readUTF();
	  tarea.setText(message); 
      tfTxt.setText(""); 
  }
  /** 
   * 图形窗口输入区域监听回车事件 
   * 
   */
  private class TFLister implements ActionListener { 
    @Override
    public void actionPerformed(ActionEvent e) { 
      String text = tfTxt.getText().trim(); 
      tarea.setText(text); 
      tfTxt.setText(""); 
      // 回车后发送数据到服务器 
      sendMessage(text); 
      try {
		getMessage();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    } 
  } 
}