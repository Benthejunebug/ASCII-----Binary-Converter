package com.benthejunebug.ASCIIBinaryConverter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

import com.benthejunebug.ASCIIBinaryConverter.conversion;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private final static String versionNumber = "V2.3";
	private String OS;
	private int height;
	private int width;
	private JMenuBar menuBar;
	private JCheckBoxMenuItem spacesCheckBox;
	private JCheckBoxMenuItem showButtonCheckBox;
	private JTextField mainField;
	private JButton toBinary;
	private JButton toASCII;
	private TextArea textArea;
	private JButton btnCopyToClipboard;
	private String output = "";
	private JButton btnX;
	private String mainFieldText;
	private boolean showButtons = false;
	public boolean resultsWindow = false;
	private boolean resized = false;
	private ImageIcon icon;

	public GUI() {

		// Sets initial JFrame properties
		super("Binary <--> ASCII Converter " + versionNumber);
		icon = new ImageIcon(getClass().getResource(
				"ASCII Binary Converter JFrame Icon.png"));
		setIconImage(icon.getImage());
		this.addComponentListener(new ComponentListener() {
			public void componentHidden(ComponentEvent arg0) {
			}

			public void componentMoved(ComponentEvent arg0) {
			}

			public void componentResized(ComponentEvent arg0) {
				if (getHeight() < height) {
					setJFrameSize();
				}
				setBounds();
				resized = true;
			}

			public void componentShown(ComponentEvent arg0) {
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		// Set System L&F
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}

		// Manages the menu bar and its contents
		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		JMenu mnOptions = new JMenu("Options");
		spacesCheckBox = new JCheckBoxMenuItem("Space in between binary");
		spacesCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				new conversion();
				if (spacesCheckBox.isSelected()) {
					conversion.setSpaces(true);
					if (resultsWindow) {
						loadResults(mainFieldText, 3);
					}
				} else {
					conversion.setSpaces(false);
					if (resultsWindow) {
						loadResults(mainFieldText, 3);
					}
				}
			}
		});
		mnOptions.add(spacesCheckBox);
		showButtonCheckBox = new JCheckBoxMenuItem("Show Buttons");
		showButtonCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (showButtonCheckBox.isSelected()) {
					showButtons = true;
					setJFrameSize();
					setBounds();
					addConvertButtons();
				} else {
					showButtons = false;
					setJFrameSize();
					setBounds();
					removeConvertButtons();
				}
			}
		});
		mnOptions.add(showButtonCheckBox);
		menuBar.add(mnOptions);
		JMenu mnHelp = new JMenu("Help");
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getContentPane(),
						"ASCII <-> Binary Converter " + versionNumber
								+ "\nCreated by Benjamin Kissinger", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnHelp.add(mntmAbout);
		menuBar.add(mnHelp);
		getContentPane().add(menuBar);

		// Manages the Entry field and its focus
		mainField = new JTextField("Enter binary or text here!");
		handlerClass enterMainFieldHandler = new handlerClass(3);
		mainField.addActionListener(enterMainFieldHandler);
		mainField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {
				mainField.selectAll();
			}

			public void focusLost(FocusEvent arg0) {
			}
		});
		mainField.selectAll();
		getContentPane().add(mainField);

		// Manages the "to Binary" button
		toBinary = new JButton("To Binary");
		handlerClass toBinaryHandler = new handlerClass(1);
		toBinary.addActionListener(toBinaryHandler);

		// Manages the "to ASCII" button
		toASCII = new JButton("To ASCII");
		handlerClass toASCIIHandler = new handlerClass(2);
		toASCII.addActionListener(toASCIIHandler);

		// Manages the JTextField
		textArea = new TextArea("", 4, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);

		// Manages the button that copys the text in the JTextArea to the
		// clipboard
		btnCopyToClipboard = new JButton("Copy To Clipboard");
		btnCopyToClipboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textTransferToClipboard copy = new textTransferToClipboard();
				copy.setClipboardContents(output);
			}
		});

		// Manages the "X" button that hides the JTextField
		btnX = new JButton("X");
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultsWindow = false;
				setJFrameSize();
				setBounds();
				removeResultsObjects();
			}
		});
		setJFrameSize();
		setBounds();
		setVisible(true);
	}

	// Gets the System's OS and checks if its windows
	public String getOS() {
		if (OS == null) {
			OS = System.getProperty("os.name");
		}
		if (OS.startsWith("Windows")) {
			return "Windows";
		} else {
			return "Other";
		}
	}

	// Sets the JFrame's size
	public void setJFrameSize() {
		int JFrameHeight = getHeight();
		int buttonsResultsHeight = 400;
		int buttonsNoResultsHeight = 124;
		int noButtonsResultsHeight = 360;
		int noButtonsNoResultsHeight = 78;
		// Sets the height in which the window will not collapse after pressing
		// btnX
		int collapseOnTextFieldCloseHeightRange = 700;
		if (resized) {
			width = getWidth();
		} else {
			width = 411;
		}
		if (showButtons) {
			if (resultsWindow) {
				if (JFrameHeight < buttonsResultsHeight) {
					if (resized) {

					}
					height = buttonsResultsHeight;
				} else {
					height = JFrameHeight;
				}
			} else {
				if (JFrameHeight < collapseOnTextFieldCloseHeightRange) {
					height = buttonsNoResultsHeight;
				} else {
					height = JFrameHeight;
				}
			}
		} else {
			if (resultsWindow) {
				if (JFrameHeight < noButtonsResultsHeight) {
					height = noButtonsResultsHeight;
				} else {
					height = JFrameHeight;
				}
			} else {
				if (JFrameHeight < collapseOnTextFieldCloseHeightRange) {
					height = noButtonsNoResultsHeight;
				} else {
					height = JFrameHeight;
				}
			}
		}
		setSize(width, height);
	}

	// Sets the bounds of all components
	public void setBounds() {
		int JFrameWidth = getWidth();
		int JFrameHeight = getHeight();
		int textAreaSideMargin = 10;
		int textAreaSouthMargin = !showButtons ? 115 : 150;
		int textAreaNorthMargin = 46;
		int convertButtonsDistanceFromBottom = 70;
		mainField.setBounds(0, 20, getWidth(), 20);
		menuBar.setBounds(0, 0, getWidth(), 21);
		toBinary.setBounds(
				(JFrameWidth - (JFrameWidth / 3)) - (toBinary.getWidth() / 2),
				JFrameHeight - convertButtonsDistanceFromBottom, 101, 23);
		toASCII.setBounds(
				(JFrameWidth - (2 * (JFrameWidth / 3)))
						- (toASCII.getWidth() / 2), JFrameHeight
						- convertButtonsDistanceFromBottom, 101, 23);
		btnCopyToClipboard.setBounds(JFrameWidth - (JFrameWidth / 2) - 75,
				JFrameHeight - textAreaSouthMargin + textAreaNorthMargin, 150,
				23);
		textArea.setBounds(
				(int) (textAreaSideMargin),
				textAreaNorthMargin,
				JFrameWidth
						- ((getOS().equals("Windows") ? 4 : 2) * textAreaSideMargin),
				JFrameHeight - textAreaSouthMargin);
		btnX.setBounds(
				(JFrameWidth - ((getOS().equals("Windows") ? 4 : 2) * textAreaSideMargin))
						- btnX.getWidth(), JFrameHeight - textAreaSouthMargin
						+ textAreaNorthMargin, 39, 23);
	}

	// Adds toBinary and toASCII to the JFrame
	public void addConvertButtons() {
		getContentPane().add(toBinary);
		getContentPane().add(toASCII);
		getContentPane().repaint();
	}

	// Removes toBianry and toASCII from the JFrame
	public void removeConvertButtons() {
		getContentPane().remove(toBinary);
		getContentPane().remove(toASCII);
		getContentPane().repaint();
	}

	// adds the results objects to the JFrame
	public void addResultsObjects() {
		getContentPane().add(btnCopyToClipboard);
		getContentPane().add(textArea);
		getContentPane().add(btnX);
		getContentPane().repaint();
	}

	// Removes the results objects from the JFrame
	void removeResultsObjects() {
		getContentPane().remove(btnCopyToClipboard);
		getContentPane().remove(textArea);
		getContentPane().remove(btnX);
		getContentPane().repaint();
	}

	// Class handles the action handlers of the "to ASCII" "to Binary" buttons
	// and the Enter of the mainField
	private class handlerClass implements ActionListener {
		private int action;

		// Constructor, clarifies the action for the converter
		private handlerClass(int x) {
			action = x;
		}

		// Handles the events
		public void actionPerformed(ActionEvent e) {
			mainFieldText = mainField.getText();
			loadResults(mainFieldText, action);
		}
	}

	// Calculates and displays the results
	public void loadResults(String mainFieldText, int action) {
		conversion convert = new conversion();
		convert.stringProcess(mainFieldText, action);
		output = convert.getReturn();
		resultsWindow = true;
		setJFrameSize();
		setBounds();
		addResultsObjects();
		textArea.setText(output);
	}
}
