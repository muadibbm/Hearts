package hearts.model;

/**
 * Indicates a misuse of a model class.
 */
@SuppressWarnings("serial")
public class ModelException extends RuntimeException 
{
	/**
	 * Generates a standard error message.
	 * @param message The message for the exception.
	 */
	public ModelException(String message) 
	{
		super(message);
	}
}
