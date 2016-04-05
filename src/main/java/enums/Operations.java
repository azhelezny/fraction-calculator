package enums;

public enum Operations {
	add("+"), sub("-"), mult("*"), div(":");

	private String operation;

	Operations(String operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		return this.operation;
	}

	public static Operations toOperation(String s){
		for(Operations operation : Operations.values())
			if(operation.toString().equals(s))
				return operation;
		return null;
	}
}
