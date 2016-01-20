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
package org.helm.notation2.parser.notation.polymer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.helm.notation2.parser.exceptionparser.NotationException;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * MonomerNotationUnitRNA class to represent nucleotides
 *
 * @author hecht
 */
public class MonomerNotationUnitRNA extends MonomerNotationUnit {

  private List<MonomerNotationUnit> contents = new ArrayList<MonomerNotationUnit>();

  /**
   * @param str
   * @param type
   * @throws IOException
   * @throws NotationException
   */
  public MonomerNotationUnitRNA(String str, String type) throws IOException, NotationException {
    super(str, type);
    setRNAContents(str);
  }

  /**
   * method to set for each nucleotide the sugar, the base and the phosphat
   *
   * @param str
   * @throws NotationException
   * @throws IOException
   */
  private void setRNAContents(String str) throws NotationException, IOException {
    /* Nucleotide with all contents */
    String[] list;
    /* nucleotide contains base element */
    if (str.contains("(")) {
      list = str.split("\\(|\\)");
      for (int i = 0; i < list.length; i++) {
        contents.add(new MonomerNotationUnit(list[i], type));
      }
    } /* nucleotide contains no base, but a modified sugar or phosphat */ else if (str.contains("[")) {
      if (str.startsWith("[")) {
        str = str.replace("]", "]$");
      } else {
        str = str.replace("[", "$[");
      }
      list = str.split("\\$");
      for (int i = 0; i < list.length; i++) {
        contents.add(new MonomerNotationUnit(list[i], type));
      }
    } /* nucleotide contains only standard sugar and/or phosphat */ else {
      for (int i = 0; i < str.length(); i++) {
        contents.add(new MonomerNotationUnit(Character.toString(str.charAt(i)),
            type));
      }
    }

  }

  /**
   * method to get the contents of this nucleotide
   *
   * @return
   */
  @JsonIgnore
  public List<MonomerNotationUnit> getContents() {
    return this.contents;
  }

}
