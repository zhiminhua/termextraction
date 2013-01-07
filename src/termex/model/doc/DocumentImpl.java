package termex.model.doc;

import java.io.IOException;
import java.net.URL;

import uk.ac.shef.wit.commons.UtilFiles;

public class DocumentImpl implements Document {

	protected URL url;
	
	 public DocumentImpl(URL url) {
		   this.url = url;
	 }
	 
	 
	
	@Override
	public URL getUrl() {
		// TODO Auto-generated method stub
		return this.url;
	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
	      String content = null;
	      try {
	         content = UtilFiles.getContent(url).toString();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      return content;
	}

	 public String toString() {
	      return url.toString();
	 }
	 
	 public boolean equals(Object o) {
	      if (this == o) return true;
	      if (o == null || getClass() != o.getClass()) return false;
	      final DocumentImpl that = (DocumentImpl) o;
	      return that.getUrl().equals(getUrl());
	 }
	 
	   public int hashCode() {
		      return url.hashCode();
	   }
}
