package models.statements;

import models.PrgState;
import models.exceptions.*;


public interface IStmt {
    PrgState execute(PrgState state) throws MyException;

    IStmt deepCopy();
}
