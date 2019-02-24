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
  // �û��������� 
  private TextField tfTxt = new TextField(); 
  // ����չʾ���� 
  private TextArea tarea = new TextArea(); 
  private Socket socket = null; 
  // ��������� 
  private DataOutputStream dataOutputStream = null; 
  public static void main(String[] args) { 
    new ChatClient().launcFrame(); 
  } 
  /** 
   * ����һ���򵥵�ͼ�λ����� 
   * 
   */
  public void launcFrame() { 
    setLocation(300, 200); 
    this.setSize(200, 400); 
    add(tfTxt, BorderLayout.SOUTH); 
    add(tarea, BorderLayout.NORTH); 
    pack(); 
    // ����ͼ�ν��洰�ڵĹر��¼� 
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
   * ���ӷ����� 
   */
  public void connect() { 
    try { 
      // �½���������� 
      socket = new Socket("127.0.0.1", 8189); 
      // ��ȡ�ͻ�������� 
      dataOutputStream = new DataOutputStream(socket.getOutputStream()); 
      System.out.println("���Ϸ����"); 
    } catch (UnknownHostException e) { 
      e.printStackTrace(); 
    } catch (IOException e) { 
      e.printStackTrace(); 
    } 
  } 
  /** 
   * �رտͻ�����Դ 
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
   * �����˷�����Ϣ 
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
   * ͼ�δ���������������س��¼� 
   * 
   */
  private class TFLister implements ActionListener { 
    @Override
    public void actionPerformed(ActionEvent e) { 
      String text = tfTxt.getText().trim(); 
      tarea.setText(text); 
      tfTxt.setText(""); 
      // �س��������ݵ������� 
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