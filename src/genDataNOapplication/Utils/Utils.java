package genDataNOapplication.Utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

//Class with different util functions to operate with JavaFX elements
public class Utils {
	

	@SuppressWarnings("static-access")
	public static Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
	    Node result = null;
	    ObservableList<Node> childrens = gridPane.getChildren();

	    for (Node node : childrens) {
	        System.out.println(node.getId());
	        System.out.println(gridPane.getColumnIndex(node));
	        if(gridPane.getRowIndex(node) == null || gridPane.getColumnIndex(node) == null) {
	        	continue;
	        }
	    	if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
	            result = node;
	            break;
	        }
	    }

	    return result;
	}
	
	public static boolean openFile(File file) {
		try {
			Desktop.getDesktop().open(file);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
