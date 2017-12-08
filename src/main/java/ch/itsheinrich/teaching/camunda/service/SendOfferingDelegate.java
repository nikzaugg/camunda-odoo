package ch.itsheinrich.teaching.camunda.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;


@Named("sendOfferingDelegate")
public class SendOfferingDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegate) throws Exception {
        System.out.println("Prozessschritt: Offerte versenden!");
    }
}