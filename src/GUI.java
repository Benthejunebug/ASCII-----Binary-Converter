import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.UIManager;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField mainField;
	private JButton toBinary;
	private JButton toASCII;
	private static String output = "";
	private static String versionNumber = "V2.0";
	private JCheckBox spacesCheckBox;
	private JCheckBox showButtonCheckBox;
	private TextArea textArea;
	private boolean alteredWindow = false;
	private JMenuBar menuBar;
	private JButton btnCopyToClipboard;
	protected boolean showButtons = false;
	public boolean results = false;
	private JButton btnX;

	public GUI() {
		super("Binary <--> ASCII Converter " + versionNumber);
		setIconImage(new ImageIcon(
				"C:\\Users\\Ben\\workspace\\ASCII converter\\src\\ASCII Binary Converter Icon.png")
				.getImage());
		setSize(330, 78);
		/*try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}*/

		mainField = new JTextField("Enter binary or text here!");
		mainField.setBounds(0, 20, 330, 20);
		toBinary = new JButton("To Binary");
		toBinary.setBounds(170, 51, 102, 23);
		toASCII = new JButton("To ASCII");
		toASCII.setBounds(44, 51, 101, 23);
		handlerClass toBinaryHandler = new handlerClass(1);
		handlerClass toASCIIHandler = new handlerClass(2);
		handlerClass enterMainFieldHandler = new handlerClass(3);
		toBinary.addActionListener(toBinaryHandler);
		toASCII.addActionListener(toASCIIHandler);
		mainField.addActionListener(enterMainFieldHandler);
		mainField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				mainField.selectAll();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		mainField.selectAll();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		getContentPane().add(mainField);
		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		menuBar.setBounds(0, 0, 330, 21);
		getContentPane().add(menuBar);

		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		spacesCheckBox = new JCheckBox("Space in between binary");
		mnOptions.add(spacesCheckBox);
		showButtonCheckBox = new JCheckBox("Show Buttons");
		mnOptions.add(showButtonCheckBox);
		showButtonCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				if (showButtonCheckBox.isSelected()) {
					showButtons = true;
					if (!results) {
						setSize(330, 124);
					} else {
						setSize(411, 400);
					}
					getContentPane().add(toBinary);
					getContentPane().add(toASCII);
				} else {
					showButtons = false;
					if (!results) {
						setSize(330, 78);
					} else {
						setSize(411, 360);
					}

					getContentPane().remove(toBinary);
					getContentPane().remove(toASCII);
				}
			}
		});
		spacesCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				if (spacesCheckBox.isSelected()) {
					new conversion().setSpaces(true);
				} else {
					new conversion().setSpaces(false);
				}
			}
		});

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);

		btnX = new JButton("X");
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showButtons) {
					setSize(330, 124);
				} else {
					setSize(330, 78);
				}
				mainField.setBounds(0, 20, 330, 20);
				menuBar.setBounds(0, 0, 330, 21);
				toBinary.setBounds(170, 51, 102, 23);
				toASCII.setBounds(44, 51, 101, 23);
				getContentPane().remove(btnCopyToClipboard);
				getContentPane().remove(textArea);
				getContentPane().remove(btnX);
				results = false;
			}
		});
		btnX.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnX.setBounds(356, 291, 39, 23);

		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getContentPane(),
						"ASCII <-> Binary Converter " + versionNumber
								+ "\nCreated by Benjamin Kissinger", "About",
						EXIT_ON_CLOSE);
			}
		});

		btnCopyToClipboard = new JButton("Copy To Clipboard");
		btnCopyToClipboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textTransferToClipboard copy = new textTransferToClipboard();
				copy.setClipboardContents(output);
			}
		});
		btnCopyToClipboard.setBounds(117, 294, 150, 23);

		textArea = new TextArea("", 4, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		textArea.setBounds(10, 46, 375, 246);
		setVisible(true);

	}

	public class handlerClass implements ActionListener {
		public int binaryOrASCIIOrEnter;
		conversion convert = new conversion();

		public handlerClass(int x) {
			if (x == 1) {
				binaryOrASCIIOrEnter = 1;
			} else if (x == 2) {
				binaryOrASCIIOrEnter = 2;
			} else if (x == 3) {
				binaryOrASCIIOrEnter = 3;
			} else {

			}

		}

		public void actionPerformed(ActionEvent e) {
			conversion convert = new conversion();
			convert.stringProcess(mainField.getText(), binaryOrASCIIOrEnter);
			output = convert.getReturn();
			if (!alteredWindow) {
				if (showButtons) {
					setSize(411, 400);
				} else {
					setSize(411, 360);
				}
				mainField.setBounds(0, 20, 411, 20);
				toBinary.setBounds(236, 328, 102, 23);
				toASCII.setBounds(45, 328, 101, 23);
				menuBar.setBounds(0, 0, 411, 21);
				getContentPane().add(btnCopyToClipboard);
				getContentPane().add(textArea);
				getContentPane().add(btnX);

			}

			textArea.setColumns(new conversion().getSpaces() ? 44 : 40);
			textArea.setText(output);
			results = true;
		}
	}
}
