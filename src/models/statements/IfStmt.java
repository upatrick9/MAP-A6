package models.statements;

import models.PrgState;
import models.adts.MyIStack;
import models.adts.MyIDictionary;
import models.exceptions.ConditionNotBoolean;
import models.exceptions.MyException;
import models.expressions.Exp;
import models.types.BoolType;
import models.values.BoolValue;
import models.values.Value;

public class IfStmt implements IStmt{
    private final Exp exp;
    private final IStmt thenS;
    private final IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        Value cond  = exp.eval(symTbl, state.getHeap());
        if(!cond.getType().equals(new BoolType()))
            throw new ConditionNotBoolean();
        BoolValue b = (BoolValue) cond;
        if(b.getValue())
            stk.push(thenS);
        else
            stk.push(elseS);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(this.exp.deepCopy(), this.thenS, this.elseS);
    }

    @Override
    public String toString() {
        return "If(" + exp.toString() + ") THEN(" + thenS + ") ELSE(" + elseS + ")";
    }
}
