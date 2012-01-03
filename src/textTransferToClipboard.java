import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;


public class textTransferToClipboard implements ClipboardOwner{
	 public void setClipboardContents( String aString ){
		    StringSelection stringSelection = new StringSelection( aString );
		    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		    clipboard.setContents( stringSelection, this );
	
	
}
	 @Override
		public void lostOwnership(java.awt.datatransfer.Clipboard clipboard,
				Transferable contents) {
			// TODO Auto-generated method stub
			
		}
}