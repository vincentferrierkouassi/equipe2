package ca.csf.calculatorgs;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import ca.csf.calculatorgs.Calculator;

public class MainActivity extends ActionBarActivity  {
	
	EditText editText, edittext2;
	
	Button button0, button1, button2, button3, button4,
		   button5, button6, button7, button8, button9;
	
	Button buttonPlus, buttonMinus, buttonMultiply, buttonDivide,
		   buttonModulo, buttonReset, buttonPoint, buttonNeg,
		   buttonFnc;

    Button buttonSin, buttonCos, buttonTan;
	
	String equation="";
	Vibrator vibrator;
	Calculator calculateur;

    AppConstants.OperationType operationType = AppConstants.OperationType.BASIC;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        calculateur = new Calculator();

        button0=(Button)findViewById(R.id.button0);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);
        button5=(Button)findViewById(R.id.button5);
        button6=(Button)findViewById(R.id.button6);
        button7=(Button)findViewById(R.id.button7);
        button8=(Button)findViewById(R.id.button8);
        button9=(Button)findViewById(R.id.button9);

        buttonPlus=(Button)findViewById(R.id.buttonPlus);
        buttonMinus=(Button)findViewById(R.id.buttonMinus);
		buttonMultiply=(Button)findViewById(R.id.buttonMultiply);
		buttonDivide=(Button)findViewById(R.id.buttonDivide);
		buttonModulo=(Button)findViewById(R.id.buttonModulo);
		buttonReset=(Button)findViewById(R.id.buttonReset);
		buttonPoint=(Button)findViewById(R.id.buttonPoint);
		buttonNeg=(Button)findViewById(R.id.buttonNeg);
		buttonFnc=(Button)findViewById(R.id.buttonFn);

        buttonSin = (Button)findViewById(R.id.buttonSin);
        buttonCos = (Button)findViewById(R.id.buttonCos);
        buttonTan = (Button)findViewById(R.id.buttonTang);

        editText=(EditText)findViewById(R.id.editText1);  
        edittext2=(EditText)findViewById(R.id.editText2);  
        
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); 
       
        editText.setText("0.0");

    }

     public void addcar(char val) {
    	vibrator.vibrate(30);
    	equation=equation+val;
        edittext2.setText(equation);
    }

    public void addString(String string) {
        vibrator.vibrate(30);
        equation=equation+string;
        edittext2.setText(equation);
    }


    public void onClickListenerFnc(View v){
    }  
     
    public void onClickListenerNeg(View v){
        addcar('-');
    }  
     
    public void onClickListenerPoint(View v){
        addcar('.');
    } 
     
    public void onClickListener0(View v){
        addcar('0');
    }
    
    public void onClickListener1(View v){
        addcar('1');
    }
    
    public void onClickListener2(View v){
        addcar('2');
    }
    
    public void onClickListener3(View v){
        addcar('3');
    }
    
    public void onClickListener4(View v){
        addcar('4');
    }
    
    public void onClickListener5(View v){
        addcar('5');
    }
    
    public void onClickListener6(View v){
        addcar('6');
    }
    
    public void onClickListener7(View v){
        addcar('7');
    }
    
    public void onClickListener8(View v){
        addcar('8');
    }
    
    public void onClickListener9(View v){
        addcar('9');
    }
    
    public void onClickListenerPlus(View v){
        if(equation != "") addcar('+');
    }

    public void onClickListenerMinus(View v){
        if(equation != "") addcar('-');
    }
    
    public void onClickListenerMultiply(View v){
        if(equation != "") addcar('*');
    }
    
    public void onClickListenerDivide(View v){
        if(equation != "") addcar('/');
    }
    
    public void onClickListenerModulo(View v){
        if(equation != "") addcar('%');
    }
    
    public void onClickListenerReset(View v){
    	vibrator.vibrate(30);
    	equation="";
        edittext2.setText(equation);
        editText.setText("0.0");
    }
	
    public void onClickListenerEqual(View v){
        vibrator.vibrate(30);
 
        calculateur.setEquation(equation);
		try {
			calculateur.calculer();
		} catch (Exception e) {
			e.printStackTrace();
		}     
        double res = calculateur.getResultat();
        String total2 = String.valueOf(res);
        editText.setText(total2);
    }

    public void onClickListenerSin(View v){
        equation = "";
        addString(AppConstants.SIN_TRIGO);
    }

    public void onClickListenerCos(View v){
        equation = "";
        addString(AppConstants.COS_TRIGO);
    }

    public void onClickListenerTan(View v){
        equation = "";
        addString(AppConstants.TAN_TRIGO);
    }
}
