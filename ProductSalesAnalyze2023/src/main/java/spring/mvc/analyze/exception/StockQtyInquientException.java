package spring.mvc.analyze.exception;

public class StockQtyInquientException extends Exception {

	public StockQtyInquientException() {
		super();
	}

	public StockQtyInquientException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StockQtyInquientException(String message, Throwable cause) {
		super(message, cause);
	}

	public StockQtyInquientException(String message) {
		super(message);
	}

	public StockQtyInquientException(Throwable cause) {
		super(cause);
	}

}
