package ExportBGG;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang.StringUtils;


import VASSAL.build.GameModule;
import VASSAL.counters.GamePiece;

import VASSAL.tools.WriteErrorDialog;
import VASSAL.tools.filechooser.FileChooser;
import VASSAL.tools.io.IOUtils;

import VASSAL.counters.Deck;
import VASSAL.counters.Stack;
import VASSAL.counters.PieceIterator;
import VASSAL.counters.Stack;


public class TextSaverBGG extends VASSAL.build.module.map.TextSaver {
	
	protected void writeMapAsText() {
	    final FileChooser fc = GameModule.getGameModule().getFileChooser();
	    if (fc.showSaveDialog(map.getView()) != FileChooser.APPROVE_OPTION) return;

	    final File file =  fc.getSelectedFile();
	    PrintWriter p = null;
	    try {
	      p = new PrintWriter(new BufferedWriter(new FileWriter(file)));

	      for (GamePiece gp : map.getPieces()) {
	    	  if (gp instanceof Deck) {
		        
		        final StringBuilder val = new StringBuilder();
		        final StringBuilder small_val = new StringBuilder();
	            final PieceIterator visibleFilter =
	              PieceIterator.visible(((Stack) gp).getPiecesReverseIterator());
	            while (visibleFilter.hasMoreElements()) {
	              final GamePiece np = visibleFilter.nextPiece();
	              //val.append(localized ? np.getLocalizedName() : np.getName());
	              // **HACK**
	              // BGG allows max of 255 size rolls.
	              // with 14 characters per deck item we still have a unique list in High Frontiers and fit within that limit.
	              small_val.append(StringUtils.left(np.getName(), 14));
	              val.append(np.getName());
	              if (val.length() > 0 && visibleFilter.hasMoreElements()) {
	                val.append(";");
	                small_val.append(";");
	              }
	            }
	            final String s = val.toString();
	            final String s_small = small_val.toString();
		        if (s.length() > 0 && s.length() < 244) {
		        	if (map.locationName(gp.getPosition()) != "offboard") 
		        		p.println( map.locationName(gp.getPosition()) + ": 1custom" + ((Stack) gp).getPieceCount() + "{" + s + "}");	        	
		        } else if (s_small.length() > 0) {
		        	if (map.locationName(gp.getPosition()) != "offboard") 
		        		p.println( map.locationName(gp.getPosition()) + ": 1custom" + ((Stack) gp).getPieceCount() + "{" + s_small + "}");
		        }
		      }
	      }

	      p.close();
	    }
	    catch (IOException e) {
	      WriteErrorDialog.error(e, file);
	    }
	    finally {
	      IOUtils.closeQuietly(p);
	    }
	  }
}
