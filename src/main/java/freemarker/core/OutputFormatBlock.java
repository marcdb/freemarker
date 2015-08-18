/*
 * Copyright 2014 Attila Szegedi, Daniel Dekany, Jonathan Revusky
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package freemarker.core;

import java.io.IOException;

import freemarker.template.TemplateException;

/**
 * An #outputFormat element
 */
final class OutputFormatBlock extends TemplateElement {
    
    private final Expression paramExp;

    OutputFormatBlock(TemplateElement nestedBlock, Expression paramExp) { 
        this.paramExp = paramExp; 
        setNestedBlock(nestedBlock);
    }

    @Override
    void accept(Environment env) throws TemplateException, IOException {
        if (getNestedBlock() != null) {
            env.visitByHiddingParent(getNestedBlock());
        }
    }

    @Override
    protected String dump(boolean canonical) {
        if (canonical) {
            String nested = getNestedBlock() != null ? getNestedBlock().getCanonicalForm() : "";
            return "<" + getNodeTypeSymbol() + " \"" + paramExp.getCanonicalForm() + "\">"
                    + nested + "</" + getNodeTypeSymbol() + ">";
        } else {
            return getNodeTypeSymbol();
        }
    }
    
    @Override
    String getNodeTypeSymbol() {
        return "#outputformat";
    }
    
    @Override
    int getParameterCount() {
        return 1;
    }

    @Override
    Object getParameterValue(int idx) {
        if (idx == 0) return paramExp;
        else
            throw new IndexOutOfBoundsException();
    }

    @Override
    ParameterRole getParameterRole(int idx) {
        if (idx == 0) return ParameterRole.VALUE;
        else
            throw new IndexOutOfBoundsException();
    }

    @Override
    boolean isIgnorable() {
        return getNestedBlock() == null || getNestedBlock().isIgnorable();
    }

    @Override
    boolean isNestedBlockRepeater() {
        return false;
    }
    
}
