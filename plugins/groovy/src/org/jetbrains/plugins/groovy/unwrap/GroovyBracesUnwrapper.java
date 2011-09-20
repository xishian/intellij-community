/*
 * Copyright 2000-2009 JetBrains s.r.o.
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
package org.jetbrains.plugins.groovy.unwrap;

import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.codeInsight.unwrap.JavaUnwrapper;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.plugins.groovy.lang.lexer.GroovyTokenTypes;
import org.jetbrains.plugins.groovy.lang.psi.GroovyFileBase;
import org.jetbrains.plugins.groovy.lang.psi.api.formatter.GrControlStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrBlockStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrCatchClause;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrIfStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrTryCatchStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.blocks.GrClosableBlock;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.blocks.GrOpenBlock;
import org.jetbrains.plugins.groovy.lang.psi.util.PsiUtil;

public class GroovyBracesUnwrapper extends GroovyUnwrapper {
  public GroovyBracesUnwrapper() {
    super(CodeInsightBundle.message("unwrap.braces"));
  }

  public boolean isApplicableTo(PsiElement e) {
    if (e instanceof GrClosableBlock && !((GrClosableBlock)e).hasParametersSection()) {
      PsiElement parent = e.getParent();
      return parent instanceof GrOpenBlock || parent instanceof GrClosableBlock || parent instanceof GroovyFileBase;
    }
    return false;
  }

  @Override
  protected void doUnwrap(PsiElement element, Context context) throws IncorrectOperationException {
    context.extractFromCodeBlock(((GrClosableBlock)element), element);
    context.delete(element);
  }
}