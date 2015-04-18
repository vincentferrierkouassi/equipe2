package ca.csf.calculatorgs;

import java.util.Vector;

public class Calculator {

	private String equation;
	private double result;
    private boolean isRadianTrigonometric = false;

    public Calculator() {
		super();
	}
	
	public void setEquation(String eq) {
		this.equation = eq;
	}

	public String getEquation() {
		return equation;
	}

	public double getResult() {
		return result;
	}

    public boolean isRadianTrigonometric() {
        return isRadianTrigonometric;
    }

    public void setRadianTrigonometric(boolean isRadianTrigo) {
        this.isRadianTrigonometric = isRadianTrigo;
    }

    public String toString() {
		return java.lang.Double.toString(result);
	}
	
	public void calculate() throws Exception {
		try {

            if (isTrigonometric(getEquation())) {
                CalculatorTrigo.isRadianTrigo = isRadianTrigonometric();
                result = CalculatorTrigo.calculate(getEquation());
            } else if (isLog(getEquation())) {
                result = CalculatorLog.calculate(getEquation());
            } else {
                Vector<StringBuffer> sousEquations = isolateSubEquations();
                result = joinSubResults(sousEquations);
            }

		} catch(NumberFormatException e) {
			throw(new Exception("Caractère(s) invalide(s)"));
		}
	}
	
	private Vector<StringBuffer> isolateSubEquations() throws Exception {
		Vector<StringBuffer> subEquations = new Vector<StringBuffer>();
		subEquations.ensureCapacity(50);
		int indexPendingSubEquation = 0;
		
		StringBuffer bufferPendingSubEquation = new StringBuffer();

		Vector<Integer> stackLastIndexes = new Vector<Integer>();
		stackLastIndexes.ensureCapacity(50);
		stackLastIndexes.add(0);
		
		char pendingCharacter;
		
		for(int I = 0; I < equation.length(); I++) {
			pendingCharacter = equation.charAt(I);
			if(pendingCharacter == '(') {
				if(indexPendingSubEquation >= subEquations.size()) {
					subEquations.add(bufferPendingSubEquation);
				}
				bufferPendingSubEquation.append('~');
				bufferPendingSubEquation.append(subEquations.size());
				bufferPendingSubEquation.append('~');
				stackLastIndexes.add(indexPendingSubEquation);
				bufferPendingSubEquation = new StringBuffer();
				subEquations.add(bufferPendingSubEquation);
				indexPendingSubEquation = subEquations.size() - 1;
			} else if(pendingCharacter == ')') {
				stackLastIndexes.remove(stackLastIndexes.size() - 1);
				indexPendingSubEquation = stackLastIndexes.get(stackLastIndexes.size() - 1);
				bufferPendingSubEquation = subEquations.get(stackLastIndexes.size() - 1);
			} else {
				if(pendingCharacter != ' ' && pendingCharacter != '\n' ) {
					bufferPendingSubEquation.append(pendingCharacter);
				}
			}
		}
		if(bufferPendingSubEquation.length() != 0 && subEquations.size() == 0) {
			subEquations.add(bufferPendingSubEquation);
		}
		if(subEquations.size() == 0) { throw(new Exception("Aucune équation!")); }
		return subEquations;
	}
	
	private double joinSubResults(Vector<StringBuffer> sousEquations) throws Exception{
		StringBuffer bufferPendingSubEquation;
		int positionReference = 0, indexReference;
		for(int I = sousEquations.size() - 1; I >= 0; I--) {
			bufferPendingSubEquation = sousEquations.get(I);
			do {
				positionReference = bufferPendingSubEquation.indexOf("~", positionReference);
				if(positionReference != -1) {
					indexReference = Integer.valueOf(bufferPendingSubEquation.substring(positionReference + 1, bufferPendingSubEquation.indexOf("~", positionReference + 1)));
					bufferPendingSubEquation = DPsReplace(bufferPendingSubEquation, "~" + indexReference + "~", sousEquations.get(indexReference));
				} else {
					break;
				}
			} while(true);
			if(bufferPendingSubEquation.length() == 0) { throw(new Exception("Couple de paranthèses vide!")); }
			Vector<String> operations = separateOperationsOfSubEquation(bufferPendingSubEquation);
			calculateOperation(operations);
			sousEquations.setElementAt(new StringBuffer(operations.get(0)),I);
		}
		return java.lang.Double.valueOf(sousEquations.get(0).toString());
	}
	
	public StringBuffer calculateOperation(Vector<String> operations) throws Exception {
		int stepOperator = 0;
		int I = 0;
		char pendingCharacter;
		while(operations.size() > 1) {
			pendingCharacter = operations.get(I).charAt(0);
			switch(stepOperator) {
			case 0: {
				if(pendingCharacter == '^') {
					operations.setElementAt(BigDecimalCalculator.pow(operations.get(I-1),operations.get(I+1)), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			case 1: {
				if(pendingCharacter == '/') {
					double testInfinity = java.lang.Double.valueOf(BigDecimalCalculator.divide(operations.get(I-1), operations.get(I+1)));
					if(java.lang.Double.toString(testInfinity).equals("Infinity")) {
						testInfinity = 0;
						throw(new Exception("Division par zéro!"));
					}
					operations.setElementAt(java.lang.Double.toString(testInfinity), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			case 2: {
				if(pendingCharacter == '\\') {
					double testInfinity = java.lang.Double.valueOf(BigDecimalCalculator.modulo(operations.get(I-1), operations.get(I+1)));
					if(java.lang.Double.toString(testInfinity).equals("Infinity")) {
						testInfinity = 0;
						throw(new Exception("Division par zéro!"));
					}
					operations.setElementAt(java.lang.Double.toString(testInfinity), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			case 3: {
				if(pendingCharacter == '*') {
					operations.setElementAt(BigDecimalCalculator.multiply(operations.get(I-1), operations.get(I+1)), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			case 4: {
				if(pendingCharacter == '-') {
					if(operations.get(I).length() == 1) {
						operations.setElementAt(BigDecimalCalculator.subtract(operations.get(I-1), operations.get(I+1)), I);
						operations.remove(I - 1);
						operations.remove(I);
					I = 0;
					}
				}
				break;
			}
			case 5: {
				if(pendingCharacter == '+') {
					operations.setElementAt(BigDecimalCalculator.add(operations.get(I-1), operations.get(I+1)), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			case 6: {
				if(pendingCharacter == '&') {
					operations.setElementAt(Integer.toString(java.lang.Integer.valueOf(operations.get(I-1)) & ((int)java.lang.Integer.valueOf(operations.get(I+1)))), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			case 7: {
				if(pendingCharacter == '%') {
					operations.setElementAt(Integer.toString(java.lang.Integer.valueOf(operations.get(I-1)) % ((int)java.lang.Integer.valueOf(operations.get(I+1)))), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			}
			if(I == operations.size() - 1) {
				if(stepOperator == 8) {
					break;
				}
				stepOperator++;
				I = 0;
			} else {
				I++;
			}
		}
		return new StringBuffer(operations.get(0));
	}

	private Vector<String> separateOperationsOfSubEquation(StringBuffer sousEquation) throws Exception {
		Vector<String> operations = new Vector<String>();
		StringBuffer pendingOperation = new StringBuffer(sousEquation.length());
		char pendingCharacter;
		boolean wasOperator = true;
		for(int I = 0; I < sousEquation.length(); I++) {
			pendingCharacter = sousEquation.charAt(I);
			switch(pendingCharacter) {
			case '+':
			case '-':
			case '*':
			case '/':
			case '&':
			case '%':
			case '\\':
			case '^': {
				if(wasOperator) {
					wasOperator = false;
					if(pendingCharacter != '-') { throw new Exception("Répétition d'opérateurs!"); }
					pendingOperation.append('-');
				} else {
					if(sousEquation.charAt(I -1) == '[' || sousEquation.charAt(I -1) == 'E') {
						pendingOperation.append('-');
					} else {
						operations.add(pendingOperation.toString());
						pendingOperation = new StringBuffer();
						operations.add(Character.toString(pendingCharacter));
						wasOperator = true;
					}
				}
				break;
			}
			default:
					pendingOperation.append(pendingCharacter);
					wasOperator = false;
			}
		}
		operations.add(pendingOperation.toString());
		return operations;
	}
		
	private static StringBuffer DPsReplace(StringBuffer data, String toFind, StringBuffer replaceBy) {
		StringBuffer result = new StringBuffer(data.length());
		int pos1, pos2 = 0;
		do {
			pos1 = data.indexOf(toFind, pos2);
			if(pos1 != -1) {
				result.append(data.substring(pos2, pos1));
				result.append(replaceBy);
				pos2 = pos1 + toFind.length();
			} else {
				result.append(data.substring(pos2));
				return result;
			}
		} while(true);
	}

    private boolean isTrigonometric(String equation) {
        boolean isTrigo = false;
        if (equation.contains(AppConstants.SIN_TRIGO)
                || equation.contains(AppConstants.COS_TRIGO)
                || equation.contains(AppConstants.TAN_TRIGO)) {
            isTrigo = true;
        }

        return  isTrigo;
    }

    private boolean isLog(String equation) {
        boolean isLog = false;
        if (equation.contains(AppConstants.LOG)
                || equation.contains(AppConstants.LOG10)) {
            isLog = true;
        }

        return  isLog;
    }


}