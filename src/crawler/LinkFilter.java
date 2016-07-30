package crawler;

/**
 *
 * @author M.Line
 *
 */
public interface LinkFilter {

	public boolean accept(String url);
}
