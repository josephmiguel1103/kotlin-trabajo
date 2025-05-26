import 'package:flutter/material.dart';
import 'dart:math' as math;

void main() {
  runApp(CalculatorApp());
}

class CalculatorApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Calculadora ',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        brightness: Brightness.dark,
      ),
      home: Calculator(),
    );
  }
}

class Calculator extends StatefulWidget {
  @override
  _CalculatorState createState() => _CalculatorState();
}

class _CalculatorState extends State<Calculator> {
  String display = '0';
  String expression = '';
  String history = '';
  double firstOperand = 0;
  String operation = '';
  bool waitingForOperand = false;
  bool justCalculated = false;

  void inputNumber(String number) {
    setState(() {
      if (waitingForOperand || justCalculated) {
        display = number;
        waitingForOperand = false;
        justCalculated = false;
        if (justCalculated) {
          expression = number;
        }
      } else {
        display = display == '0' ? number : display + number;
      }
      if (!justCalculated) {
        updateExpression(number);
      }
    });
  }

  void updateExpression(String value) {
    if (expression.isEmpty || justCalculated) {
      expression = value;
    } else {
      expression += value;
    }
  }

  void inputOperation(String nextOperation) {
    setState(() {
      double inputValue = double.tryParse(display) ?? 0;

      if (firstOperand == 0) {
        firstOperand = inputValue;
      } else if (operation.isNotEmpty && !waitingForOperand) {
        double currentResult = calculate();
        display = formatResult(currentResult);
        firstOperand = currentResult;
      }

      waitingForOperand = true;
      operation = nextOperation;
      justCalculated = false;
      
      // Actualizar expresión
      if (expression.isEmpty) {
        expression = display;
      }
      expression += ' $nextOperation ';
    });
  }

  double calculate() {
    double secondOperand = double.tryParse(display) ?? 0;
    
    switch (operation) {
      case '+':
        return firstOperand + secondOperand;
      case '-':
        return firstOperand - secondOperand;
      case '×':
        return firstOperand * secondOperand;
      case '÷':
        return secondOperand != 0 ? firstOperand / secondOperand : double.infinity;
      case '^':
        return math.pow(firstOperand, secondOperand).toDouble();
      case '%':
        return firstOperand % secondOperand;
      default:
        return secondOperand;
    }
  }

  String formatResult(double result) {
    if (result.isInfinite) return 'Error';
    if (result.isNaN) return 'Error';
    
    // Si es un número entero, mostrarlo sin decimales
    if (result % 1 == 0) {
      return result.toInt().toString();
    }
    
    // Limitar decimales para evitar números muy largos
    return result.toStringAsFixed(8).replaceAll(RegExp(r'0*$'), '').replaceAll(RegExp(r'\.$'), '');
  }

  void performCalculation() {
    setState(() {
      double result = calculate();
      String resultStr = formatResult(result);
      
      // Guardar en historial
      history = expression + ' = ' + resultStr;
      
      display = resultStr;
      expression = resultStr;
      firstOperand = 0;
      operation = '';
      waitingForOperand = true;
      justCalculated = true;
    });
  }

  void clear() {
    setState(() {
      display = '0';
      expression = '';
      firstOperand = 0;
      operation = '';
      waitingForOperand = false;
      justCalculated = false;
    });
  }

  void clearHistory() {
    setState(() {
      history = '';
    });
  }

  void inputDecimal() {
    setState(() {
      if (waitingForOperand || justCalculated) {
        display = '0.';
        waitingForOperand = false;
        justCalculated = false;
        expression = justCalculated ? '0.' : expression + '0.';
      } else if (!display.contains('.')) {
        display = display + '.';
        expression += '.';
      }
    });
  }

  void calculatePercentage() {
    setState(() {
      double value = double.tryParse(display) ?? 0;
      double result = value / 100;
      display = formatResult(result);
      expression = display;
      justCalculated = true;
    });
  }

  void calculateSquareRoot() {
    setState(() {
      double value = double.tryParse(display) ?? 0;
      if (value < 0) {
        display = 'Error';
      } else {
        double result = math.sqrt(value);
        display = formatResult(result);
      }
      expression = '√($value)';
      justCalculated = true;
    });
  }

  void calculatePi() {
    setState(() {
      display = formatResult(math.pi);
      expression = 'π';
      justCalculated = true;
    });
  }

  void calculateE() {
    setState(() {
      display = formatResult(math.e);
      expression = 'e';
      justCalculated = true;
    });
  }

  void toggleSign() {
    setState(() {
      if (display != '0' && !display.contains('Error')) {
        if (display.startsWith('-')) {
          display = display.substring(1);
        } else {
          display = '-' + display;
        }
      }
    });
  }

  Widget buildButton(String text, {Color? color, Color? textColor, double? fontSize, VoidCallback? onPressed}) {
    return Expanded(
      child: Container(
        height: 70,
        margin: EdgeInsets.all(2),
        child: ElevatedButton(
          style: ElevatedButton.styleFrom(
            backgroundColor: color ?? Colors.grey[800],
            foregroundColor: textColor ?? Colors.white,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10),
            ),
            elevation: 3,
          ),
          onPressed: onPressed ?? () => handleButtonPress(text),
          child: Text(
            text,
            style: TextStyle(
              fontSize: fontSize ?? 18,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
      ),
    );
  }

  void handleButtonPress(String text) {
    switch (text) {
      case 'C':
        clear();
        break;
      case 'CE':
        clearHistory();
        break;
      case '=':
        performCalculation();
        break;
      case '.':
        inputDecimal();
        break;
      case '%':
        if (operation.isEmpty) {
          calculatePercentage();
        } else {
          inputOperation('%');
        }
        break;
      case '√':
        calculateSquareRoot();
        break;
      case 'π':
        calculatePi();
        break;
      case 'e':
        calculateE();
        break;
      case '±':
        toggleSign();
        break;
      case '+':
      case '-':
      case '×':
      case '÷':
      case '^':
        inputOperation(text);
        break;
      default:
        inputNumber(text);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      appBar: AppBar(
        title: Text('Calculadora Avanzada'),
        backgroundColor: Colors.grey[900],
        foregroundColor: Colors.white,
        actions: [
          IconButton(
            icon: Icon(Icons.history),
            onPressed: clearHistory,
            tooltip: 'Limpiar historial',
          ),
        ],
      ),
      body: Column(
        children: [
          // Historial y expresión actual
          Container(
            width: double.infinity,
            padding: EdgeInsets.all(16),
            color: Colors.grey[900],
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                // Historial
                if (history.isNotEmpty)
                  Text(
                    history,
                    style: TextStyle(
                      color: Colors.grey[400],
                      fontSize: 16,
                    ),
                    textAlign: TextAlign.right,
                  ),
                SizedBox(height: 8),
                // Expresión actual
                Text(
                  expression.isEmpty ? display : expression,
                  style: TextStyle(
                    color: Colors.grey[300],
                    fontSize: 20,
                  ),
                  textAlign: TextAlign.right,
                ),
              ],
            ),
          ),
          
          // Pantalla principal (resultado)
          Container(
            width: double.infinity,
            padding: EdgeInsets.all(20),
            color: Colors.black,
            child: Text(
              display.length > 10 ? display.substring(0, 10) + '...' : display,
              style: TextStyle(
                color: Colors.white,
                fontSize: 48,
                fontWeight: FontWeight.bold,
              ),
              textAlign: TextAlign.right,
            ),
          ),
          
          // Botones de la calculadora
          Expanded(
            child: Container(
              padding: EdgeInsets.all(8),
              child: Column(
                children: [
                  // Primera fila - Funciones especiales
                  Expanded(
                    child: Row(
                      children: [
                        buildButton('C', color: Colors.red[700]),
                        buildButton('CE', color: Colors.red[600]),
                        buildButton('√', color: Colors.blue[700]),
                        buildButton('^', color: Colors.orange[700]),
                      ],
                    ),
                  ),
                  
                  // Segunda fila - Constantes y operadores
                  Expanded(
                    child: Row(
                      children: [
                        buildButton('π', color: Colors.purple[700]),
                        buildButton('e', color: Colors.purple[700]),
                        buildButton('%', color: Colors.green[700]),
                        buildButton('÷', color: Colors.orange[700]),
                      ],
                    ),
                  ),
                  
                  // Tercera fila
                  Expanded(
                    child: Row(
                      children: [
                        buildButton('7'),
                        buildButton('8'),
                        buildButton('9'),
                        buildButton('×', color: Colors.orange[700]),
                      ],
                    ),
                  ),
                  
                  // Cuarta fila
                  Expanded(
                    child: Row(
                      children: [
                        buildButton('4'),
                        buildButton('5'),
                        buildButton('6'),
                        buildButton('-', color: Colors.orange[700]),
                      ],
                    ),
                  ),
                  
                  // Quinta fila
                  Expanded(
                    child: Row(
                      children: [
                        buildButton('1'),
                        buildButton('2'),
                        buildButton('3'),
                        buildButton('+', color: Colors.orange[700]),
                      ],
                    ),
                  ),
                  
                  // Sexta fila
                  Expanded(
                    child: Row(
                      children: [
                        buildButton('±', color: Colors.grey[700]),
                        buildButton('0'),
                        buildButton('.'),
                        buildButton('=', color: Colors.green[700]),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}