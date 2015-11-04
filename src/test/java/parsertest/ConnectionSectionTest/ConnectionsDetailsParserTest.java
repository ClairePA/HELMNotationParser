/**
 * *****************************************************************************
 * Copyright C 2015, The Pistoia Alliance
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *****************************************************************************
 */
package parsertest.ConnectionSectionTest;

import org.testng.annotations.Test;
import parsertest.InlineAnnotationsParser;
import parsertest.StateMachineParser;
import parsertest.ConnectionSection.ConnectionsDetailsParser;
import parsertest.ConnectionSection.ConnectionsParser;
import parsertest.ExceptionParser.ConnectionSectionException;
import parsertest.ExceptionParser.ExceptionState;
import parsertest.GroupingSection.GroupingParser;

public class ConnectionsDetailsParserTest {

	StateMachineParser parser;
	@Test 
  	public void keepThisState() throws ExceptionState {
		parser = new StateMachineParser();
		parser.setState(new ConnectionsDetailsParser(parser));
    parser.doAction('H');
		
		if(!(parser.getState() instanceof ConnectionsDetailsParser)){
			throw new ConnectionSectionException("");
		}  
	}
	
	@Test 
  	public void goToGroupingParser() throws ExceptionState {
		parser = new StateMachineParser();
    parser.setState(new ConnectionsParser(parser));
    String test = "PEPTIDE1,PEPTIDE2,C:R3-?:?$";
		for(int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
		
		if(!(parser.getState() instanceof GroupingParser)){
			throw new ConnectionSectionException("");
		}  
	}
	
	@Test(expectedExceptions = ConnectionSectionException.class)
	public void goToGroupingParserWithException() throws ExceptionState {
		parser = new StateMachineParser();
    parser.setState(new ConnectionsParser(parser));
    String test = "PEPTIDE1,PEPTIDE2,hallo$";
		for(int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
	}
	
	@Test 
  	public void goToInlineAnnotationsParser() throws ExceptionState {
		parser = new StateMachineParser();
    parser.setState(new ConnectionsParser(parser));
    String test = "PEPTIDE1,PEPTIDE2,C:R3-?:?\"";
		for(int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
		
		if(!(parser.getState() instanceof InlineAnnotationsParser)){
			throw new ConnectionSectionException("");
		}  
	}
	
	@Test(expectedExceptions = ConnectionSectionException.class)
	public void goToInlineAnnotationsParserWithException() throws ExceptionState {
		parser = new StateMachineParser();
    parser.setState(new ConnectionsParser(parser));
    String test = "PEPTIDE1,PEPTIDE2,hallo\"";
		for(int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
	}
	
	
}
