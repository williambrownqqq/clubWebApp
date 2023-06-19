package com.masterswork.mail.exhendler.interpolator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionException;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import java.util.Map;

@Slf4j
public class SpelMessageInterpolator {

    private final EvaluationContext evalContext;
    private final SpelExpressionParser spelExpressionParser;

    public SpelMessageInterpolator() {
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        ctx.addPropertyAccessor(new MapAccessor());
        this.evalContext = ctx;
        this.spelExpressionParser = new SpelExpressionParser();
    }

    public SpelMessageInterpolator(EvaluationContext evalContext) {
        Assert.notNull(evalContext, "Evaluation context must not be null");
        this.evalContext = evalContext;
        this.spelExpressionParser = new SpelExpressionParser();
    }

    public String interpolate(String messageTemplate, Map<String, Object> variables) {
        Assert.notNull(messageTemplate, "messageTemplate must not be null");
        try {
            Expression expression = spelExpressionParser.parseExpression(messageTemplate, new TemplateParserContext());
            return expression.getValue(evalContext, variables, String.class);
        } catch (ExpressionException ex) {
            log.error(String.format("Failed to interpolate message template: '%s'", messageTemplate), ex);
        }
        return messageTemplate;
    }
}
