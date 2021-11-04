package hw4_201723018;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ToolTipManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Hw4_201723018 extends JFrame {

	JTextArea tf = new JTextArea(50, 50);
	File file;
	// 불러온 txt 파일의 내용을 저장할 String 변수
	String txtFieldString = "";

	public Hw4_201723018() {
		setTitle("Hw4_201723018    제목없음-txt편집기");
		// 새 파일을 닫았을 때 모두 종료되지 않도록 DISPOSE_ON_CLOSE함
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 600);

		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(tf, BorderLayout.CENTER);

		// 메뉴바
		JMenuBar menuBar = new JMenuBar();
		// 메뉴바 아이템
		JMenuItem newFile = new JMenuItem("새 txt 파일");
		JMenuItem loadFile = new JMenuItem("txt불러 오기");
		// 저장 버튼, 툴바 아이템
		JButton save = new JButton("Save");
		save.setToolTipText("파일을 저장합니다.");
		// toolbar 매니저
		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.setInitialDelay(100);
		// 메뉴
		JMenu fileMenu = new JMenu("txt파일");
		// 툴바
		JToolBar toolBar = new JToolBar("툴바");

		fileMenu.add(newFile);
		fileMenu.add(loadFile);
		menuBar.add(fileMenu);
		toolBar.add(save);

		setJMenuBar(menuBar);
		c.add(toolBar, BorderLayout.NORTH);

		setVisible(true);

		// 불러오기 액션 메소드
		loadFile.addActionListener(new ActionListener() {
			// 파일 다이얼로그
			JFileChooser txtFileChooser;

			// 불러온 txt파일의 내용을 TextArea에 setText함
			@Override
			public void actionPerformed(ActionEvent e) {
				txtFileChooser = new JFileChooser();

				txtFileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
				int ret = txtFileChooser.showOpenDialog(null);
				// 그냥 닫았을 때
				if (ret != JFileChooser.APPROVE_OPTION) {
					return;
				}

				file = txtFileChooser.getSelectedFile();
				try {
					BufferedReader txtReader = new BufferedReader(new FileReader(file));
					// title 수정
					setTitle("Hw4_201723018    " + file.getName() + "-txt편집기");
					String line = null;
					while ((line = txtReader.readLine()) != null) {
						txtFieldString += line + "\n";
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				tf.setText(txtFieldString);
				// 텍스트버퍼 String 초기화
				txtFieldString = "";
			}
		});

		// 저장하기 액션 메소드
		save.addActionListener(new ActionListener() {
			// 파일 다이얼로그
			JFileChooser txtFileChooser;

			@Override
			public void actionPerformed(ActionEvent e) {
				// 파일 위치 설정
				if (file == null) {
					txtFileChooser = new JFileChooser();
					txtFileChooser.setDialogTitle("저장하기");
					txtFileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
					int save = txtFileChooser.showSaveDialog(null);
					if (save == JFileChooser.APPROVE_OPTION) {
						file = txtFileChooser.getSelectedFile();
						saveTxt();
						return;
					}
				} else {
					saveTxt();
				}

			}

			// 파일 저장 메소드
			public void saveTxt() {

				try {
					BufferedWriter txtWriter;
					if (file.toString().contains(".txt")) {
						txtWriter = new BufferedWriter(new FileWriter(file));
					} else {
						txtWriter = new BufferedWriter(new FileWriter(file + ".txt"));
					}
					txtWriter.write(tf.getText());
					txtWriter.flush();
					txtWriter.close();
					int result = JOptionPane.showConfirmDialog(save.getTopLevelAncestor(), "저장 되었습니다.", "저장 성공",
							JOptionPane.PLAIN_MESSAGE);
					// title 수정
					setTitle("Hw4_201723018    " + file.getName() + "-txt편집기");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// 새 파일 액션 메소드
		newFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Hw4_201723018 newFrame = new Hw4_201723018();
				newFrame.setLocation(400, 0);
			}
		});
	}

	public static void main(String[] args) {
		new Hw4_201723018();
	}

}
