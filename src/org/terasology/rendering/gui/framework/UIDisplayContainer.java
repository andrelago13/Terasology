/*
 * Copyright 2011 Benjamin Glatzel <benjamin.glatzel@me.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.rendering.gui.framework;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector4f;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.terasology.rendering.gui.framework.style.UIStyle;

/**
 * Composition of multiple display elements.
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public abstract class UIDisplayContainer extends UIDisplayElement {

    final ArrayList<UIDisplayElement> _displayElements = new ArrayList<UIDisplayElement>();
    private boolean _crop               = false;

    private UIStyle _style       = null;

    private Vector4f _cropMargin = new Vector4f(/*TOP*/    0.0f,
                                                /*RIGHT*/  0.0f,
                                                /*BOTTOM*/ 0.0f,
                                                /*LEFT*/   0.0f
    );

    public UIDisplayContainer() {
        super();
    }

    public UIDisplayContainer(Vector2f position) {
        super(position);
    }

    public UIDisplayContainer(Vector2f position, Vector2f size) {
        super(position, size);
    }

    public void render() {
        boolean testCrop = false;
        int cropX      = 0;
        int cropY      = 0;
        int cropWidth  = 0;
        int cropHeight = 0;

        if (!isVisible())
            return;

        //Cut the elements
        if(_crop){
            cropX      = (int)getPosition().x - (int)(_cropMargin.w);
            cropY      = Display.getHeight()-((int)getPosition().y + (int)getSize().y + (int)_cropMargin.z);
            cropWidth  = (int)getSize().x + (int)_cropMargin.y;
            cropHeight = (int)getSize().y + (int)_cropMargin.x + (int)_cropMargin.z;
            glEnable(GL_SCISSOR_TEST);
            glScissor(cropX, cropY, cropWidth, cropHeight);
        }

        // Render all display elements
        for (int i = 0; i < _displayElements.size(); i++) {
            testCrop = _crop&&!_displayElements.get(i).isCroped();
            if(testCrop){
                glDisable(GL_SCISSOR_TEST);
            }
            _displayElements.get(i).renderTransformed();
            if(testCrop){
                glEnable(GL_SCISSOR_TEST);
                glScissor(cropX, cropY, cropWidth, cropHeight);
            }
        }

        if(_crop){
            glDisable(GL_SCISSOR_TEST);
        }
    }

    public void update() {
        if (!isVisible())
            return;

        // Update all display elements
        for (int i = 0; i < _displayElements.size(); i++) {
            _displayElements.get(i).update();
        }
    }

    @Override
    public void processKeyboardInput(int key) {
        if (!isVisible())
            return;

        super.processKeyboardInput(key);

        // Pass the pressed key to all display elements
        for (int i = 0; i < _displayElements.size(); i++) {
            _displayElements.get(i).processKeyboardInput(key);
        }
    }

    @Override
    public void processMouseInput(int button, boolean state, int wheelMoved) {
        if (!isVisible())
            return;

        super.processMouseInput(button, state, wheelMoved);

        // Pass the mouse event to all display elements
        for (int i = 0; i < _displayElements.size(); i++) {
            _displayElements.get(i).processMouseInput(button, state, wheelMoved);
        }
    }

    public void addDisplayElement(UIDisplayElement element) {
        _displayElements.add(element);
        element.setParent(this);
    }

    public void addFirstDisplayElement(UIDisplayElement element) {
        _displayElements.add(0,element);
        element.setParent(this);
    }

    public void removeDisplayElement(UIDisplayElement element) {
        _displayElements.remove(element);
        element.setParent(null);
    }

    public ArrayList<UIDisplayElement> getDisplayElements() {
        return _displayElements;
    }

    /*
     * Set the option for cut elements
     */
    public void setCrop(boolean crop){
        _crop = crop;
    }

    public void setCropMargin(Vector4f margin){
        _cropMargin = margin;
    }

    public void setStyle(String property, String value){
        if(_style==null){
            _style = new UIStyle(getSize());
            _style.setPosition(new Vector2f(0f,0f));
            _style.setVisible(true);
            _style.setCroped(false);
            addFirstDisplayElement(_style);
        }
        _style.parse(property,value);
    }

}
