package ca.csf.calculatorgs;

import java.util.Vector;

public class Calculator {

	private String equation;
	private double resultat;
    private boolean isRadianTrigo = false;

    public Calculator() {
		super();
	}
	
	public void setEquation(String eq) {
		this.equation = eq;
	}

	public String getEquation() {
		return equation;
	}

	public double getResultat() {
		return resultat;
	}

    public boolean isRadianTrigo() {
        return isRadianTrigo;
    }

    public void setRadianTrigo(boolean isRadianTrigo) {
        this.isRadianTrigo = isRadianTrigo;
    }

    public String toString() {
		return java.lang.Double.toString(resultat);
	}
	
	public void calculer() throws Exception {
		try {

            if (isTrigo(getEquation())) {
                CalculatorTrigo.isRadianTrigo = isRadianTrigo();
                resultat = CalculatorTrigo.calculer(getEquation());
            } else if (isLog(getEquation())) {
                resultat = CalculatorLog.calculer(getEquation());
            } else {
                Vector<StringBuffer> sousEquations = isolerSousEquations();
                resultat = joindreSousResultats(sousEquations);
            }

		} catch(NumberFormatException e) {
			throw(new Exception("Caract�re(s) invalide(s)"));
		}
	}
	
	private Vector<StringBuffer> isolerSousEquations() throws Exception {
		Vector<StringBuffer> sousEquations = new Vector<StringBuffer>();
		sousEquations.ensureCapacity(50);
		int indexSousEquationEnCours = 0;
		
		StringBuffer bufferSousEquationEnCours = new StringBuffer();

		Vector<Integer> stackDerniersIndexes = new Vector<Integer>();
		stackDerniersIndexes.ensureCapacity(50);
		stackDerniersIndexes.add(0);
		
		char caractereEnCours;
		
		for(int I = 0; I < equation.length(); I++) {
			caractereEnCours = equation.charAt(I);
			if(caractereEnCours == '(') {
				if(indexSousEquationEnCours >= sousEquations.size()) {
					sousEquations.add(bufferSousEquationEnCours);
				}
				bufferSousEquationEnCours.append('~');
				bufferSousEquationEnCours.append(sousEquations.size());
				bufferSousEquationEnCours.append('~');
				stackDerniersIndexes.add(indexSousEquationEnCours);
				bufferSousEquationEnCours = new StringBuffer();
				sousEquations.add(bufferSousEquationEnCours);
				indexSousEquationEnCours = sousEquations.size() - 1;
			} else if(caractereEnCours == ')') {
				stackDerniersIndexes.remove(stackDerniersIndexes.size() - 1);
				indexSousEquationEnCours = stackDerniersIndexes.get(stackDerniersIndexes.size() - 1);
				bufferSousEquationEnCours = sousEquations.get(stackDerniersIndexes.size() - 1);
			} else {
				if(caractereEnCours != ' ' && caractereEnCours != '\n' ) {
					bufferSousEquationEnCours.append(caractereEnCours);
				}
			}
		}
		if(bufferSousEquationEnCours.length() != 0 && sousEquations.size() == 0) {
			sousEquations.add(bufferSousEquationEnCours);
		}
		if(sousEquations.size() == 0) { throw(new Exception("Aucune �quation!")); }
		return sousEquations;
	}
	
	private double joindreSousResultats(Vector<StringBuffer> sousEquations) throws Exception{
		StringBuffer bufferSousEquationEnCours;
		int positionReference = 0, indexReference;
		for(int I = sousEquations.size() - 1; I >= 0; I--) {
			bufferSousEquationEnCours = sousEquations.get(I);
			do {
				positionReference = bufferSousEquationEnCours.indexOf("~", positionReference);
				if(positionReference != -1) {
					indexReference = Integer.valueOf(bufferSousEquationEnCours.substring(positionReference + 1, bufferSousEquationEnCours.indexOf("~", positionReference + 1)));
					bufferSousEquationEnCours = DPsReplace(bufferSousEquationEnCours, "~" + indexReference + "~", sousEquations.get(indexReference));
				} else {
					break;
				}
			} while(true);
			if(bufferSousEquationEnCours.length() == 0) { throw(new Exception("Couple de paranth�ses vide!")); }
			Vector<String> operations = separerOperationsDeSousEquation(bufferSousEquationEnCours);
			calculerOperation(operations);
			sousEquations.setElementAt(new StringBuffer(operations.get(0)),I);
		}
		return java.lang.Double.valueOf(sousEquations.get(0).toString());
	}
	
	public StringBuffer calculerOperation(Vector<String> operations) throws Exception {
		int etapeOperateur = 0;
		int I = 0;
		char caractereEnCours;
		while(operations.size() > 1) {
			caractereEnCours = operations.get(I).charAt(0);
			switch(etapeOperateur) {
			case 0: {
				if(caractereEnCours == '^') {
					operations.setElementAt(java.lang.Double.toString(Math.pow(java.lang.Double.valueOf(operations.get(I-1)),java.lang.Double.valueOf(operations.get(I+1)))), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			case 1: {
				if(caractereEnCours == '/') {
					double testInfinity = java.lang.Double.valueOf(operations.get(I-1))/java.lang.Double.valueOf(operations.get(I+1));
					if(java.lang.Double.toString(testInfinity).equals("Infinity")) {
						testInfinity = 0;
						throw(new Exception("Division par z�ro!"));
					}
					operations.setElementAt(java.lang.Double.toString(testInfinity), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			case 2: {
				if(caractereEnCours == '\\') {
					double testInfinity = java.lang.Double.valueOf(operations.get(I-1)) % java.lang.Double.valueOf(operations.get(I+1));
					if(java.lang.Double.toString(testInfinity).equals("Infinity")) {
						testInfinity = 0;
						throw(new Exception("Division par z�ro!"));
					}
					operations.setElementAt(java.lang.Double.toString(testInfinity), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			case 3: {
				if(caractereEnCours == '*') {
					operations.setElementAt(java.lang.Double.toString(java.lang.Double.valueOf(operations.get(I-1))*java.lang.Double.valueOf(operations.get(I+1))), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			case 4: {
				if(caractereEnCours == '-') {
					if(operations.get(I).length() == 1) {
						operations.setElementAt(java.lang.Double.toString(java.lang.Double.valueOf(operations.get(I-1))-java.lang.Double.valueOf(operations.get(I+1))), I);
						operations.remove(I - 1);
						operations.remove(I);
					I = 0;
					}
				}
				break;
			}
			case 5: {
				if(caractereEnCours == '+') {
					operations.setElementAt(java.lang.Double.toString(java.lang.Double.valueOf(operations.get(I-1))+java.lang.Double.valueOf(operations.get(I+1))), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			case 6: {
				if(caractereEnCours == '&') {
					operations.setElementAt(Integer.toString(java.lang.Integer.valueOf(operations.get(I-1)) & ((int)java.lang.Integer.valueOf(operations.get(I+1)))), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			case 7: {
				if(caractereEnCours == '%') {
					operations.setElementAt(Integer.toString(java.lang.Integer.valueOf(operations.get(I-1)) ^ ((int)java.lang.Integer.valueOf(operations.get(I+1)))), I);
					operations.remove(I - 1);
					operations.remove(I);
					I = 0;
				}
				break;
			}
			}
			if(I == operations.size() - 1) {
				if(etapeOperateur == 8) {
					break;
				}
				etapeOperateur++;
				I = 0;
			} else {
				I++;
			}
		}
		return new StringBuffer(operations.get(0));
	}

	private Vector<String> separerOperationsDeSousEquation(StringBuffer sousEquation) throws Exception {
		Vector<String> operations = new Vector<String>();
		StringBuffer operationEnCours = new StringBuffer(sousEquation.length());
		char caractereEnCours;
		boolean etaitOperateur = true;
		for(int I = 0; I < sousEquation.length(); I++) {
			caractereEnCours = sousEquation.charAt(I);
			switch(caractereEnCours) {
			case '+':
			case '-':
			case '*':
			case '/':
			case '&':
			case '%':
			case '\\':
			case '^': {
				if(etaitOperateur) {
					etaitOperateur = false;
					if(caractereEnCours != '-') { throw new Exception("R�p�tition d'op�rateurs!"); }
					operationEnCours.append('-');
				} else {
					if(sousEquation.charAt(I -1) == '[' || sousEquation.charAt(I -1) == 'E') {
						operationEnCours.append('-');
					} else {
						operations.add(operationEnCours.toString());
						operationEnCours = new StringBuffer();
						operations.add(Character.toString(caractereEnCours));
						etaitOperateur = true;
					}
				}
				break;
			}
			default:
					operationEnCours.append(caractereEnCours);
					etaitOperateur = false;
			}
		}
		operations.add(operationEnCours.toString());
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

    private boolean isTrigo(String equation) {
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