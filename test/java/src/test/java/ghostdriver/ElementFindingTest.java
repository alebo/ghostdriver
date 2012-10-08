/*
This file is part of the GhostDriver project from Neustar inc.

Copyright (c) 2012, Ivan De Marino <ivan.de.marino@gmail.com / detronizator@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package ghostdriver;

import org.junit.Test;
import org.openqa.selenium.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElementFindingTest extends BaseTest {
    @Test
    public void findChildElement() {
        WebDriver d = getDriver();

        d.get("http://www.yahoo.com");
        WebElement parent = d.findElement(By.id("y-masthead"));

        WebElement child = parent.findElement(By.name("p"));
    }

    @Test
    public void findChildElements() {
        WebDriver d = getDriver();

        d.get("http://www.yahoo.com");
        WebElement parent = d.findElement(By.id("y-masthead"));

        List<WebElement> children = parent.findElements(By.tagName("input"));

        assertEquals(5, children.size());
    }

    @Test
    public void findMultipleElements() {
        WebDriver d = getDriver();

        d.get("http://www.google.com");
        List<WebElement> els = d.findElements(By.tagName("input"));

        assertTrue(els.size() >= 6 && els.size() <= 8);
    }

    @Test
    public void findNoElementsMeetingCriteria() {
        WebDriver d = getDriver();

        d.get("http://www.google.com");
        List<WebElement> els = d.findElements(By.name("noElementWithThisName"));

        assertEquals(0, els.size());
    }

    @Test
    public void findNoChildElementsMeetingCriteria() {
        WebDriver d = getDriver();

        d.get("http://www.google.com");
        WebElement parent = d.findElement(By.name("q"));

        List<WebElement> children = parent.findElements(By.tagName("input"));

        assertEquals(0, children.size());
    }

    @Test
    public void findActiveElement() {
        WebDriver d = getDriver();

        d.get("http://www.google.com");
        WebElement inputField = d.findElement(By.cssSelector("input[name='q']"));
        WebElement active = d.switchTo().activeElement();

        assertEquals(inputField.getTagName(), active.getTagName());
        assertEquals(inputField.getLocation(), active.getLocation());
        assertEquals(inputField.hashCode(), active.hashCode());
        assertEquals(inputField.getText(), active.getText());
        assertTrue(inputField.equals(active));
    }

    @Test(expected = NoSuchElementException.class)
    public void failToFindNonExistentElement() {
        WebDriver d = getDriver();

        d.get("http://www.google.com");
        WebElement inputField = d.findElement(By.cssSelector("input[name='idontexist']"));
    }

    @Test(expected = InvalidSelectorException.class)
    public void failFindElementForInvalidXPathLocator() {
        WebDriver d = getDriver();

        d.get("http://www.google.com");
        WebElement inputField = d.findElement(By.xpath("this][isnot][valid"));
    }

    @Test(expected = InvalidSelectorException.class)
    public void failFindElementsForInvalidXPathLocator() {
        WebDriver d = getDriver();

        d.get("http://www.google.com");
        List<WebElement> inputField = d.findElements(By.xpath("this][isnot][valid"));
    }
}
