package camunda.gepjarmu;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Component
public class Gepjarmu {

    @Autowired
    private ZeebeClient zeebeClient;
    private final Random random = new Random();

    private HashMap<String, Boolean> generateBooleanHashMap(String s, int successChance) {
        HashMap<String, Boolean> variables = new HashMap<>();

        if (this.random.nextInt(0, 100) < successChance) {
            variables.put(s, true);
        } else {
            variables.put(s, false);
        }

        return variables;
    }

    private HashMap<String, Boolean> generateBooleanHashMap(String s, boolean b) {
        HashMap<String, Boolean> variables = new HashMap<>();
        variables.put(s, b);
        return variables;
    }

    @ZeebeWorker(type = "establishReason", autoComplete = true)
    public HashMap<String, Boolean> establishReason() {
        HashMap<String, Boolean> variables = new HashMap<>();
        variables.put("isAgainstLaw", random.nextInt(0, 100) < 5);
        variables.put("isReplacement", random.nextInt(0, 100) < 50);
        variables.put("isMisappropriated", random.nextInt(0, 100) < 30);

        return variables;
    }

    @ZeebeWorker(type = "againstLaw", autoComplete = true)
    public void againstLaw() throws InterruptedException {
        zeebeClient.newPublishMessageCommand().messageName("againstLaw").correlationKey("againstLaw").send().join();
    }

    @ZeebeWorker(type = "sendFromPoliceToGov", autoComplete = true)
    public void sendFromPoliceToGov() {
        zeebeClient.newPublishMessageCommand().messageName("messageFromPolice").correlationKey("messageFromPolice").send().join();
    }

    @ZeebeWorker(type = "withdrawalDrivingLicence", autoComplete = true)
    public void withdrawalDrivingLicence() {
        zeebeClient.newPublishMessageCommand().messageName("NotifyClient").correlationKey("drivingLicence").send().join();
    }

    @ZeebeWorker(type = "reportToPolice", autoComplete = true)
    public void reportToPolice() {
        zeebeClient.newPublishMessageCommand().messageName("ReportToPolice").correlationKey("ReportToPolice").send().join();
    }

    @ZeebeWorker(type = "sendReportCopyToCustomer", autoComplete = true)
    public void sendReportCopyToCustomer() {
        zeebeClient.newPublishMessageCommand().messageName("PoliceReport").correlationKey("report").send().join();
    }

    @ZeebeWorker(type = "submitApplication", autoComplete = true)
    public void submitApplication(final ActivatedJob job) {
        zeebeClient.newPublishMessageCommand().messageName("ApplicationArrival").correlationKey("ApplicationArrival").variables(job.getVariablesAsMap()).send().join();
    }

    @ZeebeWorker(type = "sendSupplementarySubmission", autoComplete = true)
    public void sendSupplementarySubmission() {
        zeebeClient.newPublishMessageCommand().messageName("SupplementarySubmissionArrival").correlationKey("arrived").send().join();
    }

    @ZeebeWorker(type = "checkId", autoComplete = true)
    public HashMap<String, Boolean> checkId() {
        return generateBooleanHashMap("IdOk", 90);
    }

    @ZeebeWorker(type = "CheckDocuments", autoComplete = true)
    public void CheckDocuments(final ActivatedJob job) {
        String correlationKey = null;
        Map<String, Object> variables = job.getVariablesAsMap();

        switch (random.nextInt(0, 7)) {
            case 0:
                correlationKey = "forgalmba";
                variables.put("isDemolition", random.nextBoolean());
                break;
            case 1:
                correlationKey = "atrendszam";
                break;
            case 2:
                correlationKey = "torzskonyv";
                break;
            case 3:
                correlationKey = "vezetoi";
                break;
            case 4:
                correlationKey = "atiras";
                break;
            case 5:
                correlationKey = "kulfoldi";
                break;
            case 6:
                correlationKey = "uzembentarto";
                break;
        }
        System.out.println(correlationKey);
        variables.put("correlationKey", correlationKey);

        zeebeClient.newPublishMessageCommand().messageName(correlationKey).correlationKey(correlationKey).variables(variables).send().join();
    }

    @ZeebeWorker(type = "checkTrafficPermit", autoComplete = true)
    public HashMap<String, Boolean> checkTrafficPermit() {
        return generateBooleanHashMap("trafficPermitOk", 80);
    }

    @ZeebeWorker(type = "checkPedigreeBook", autoComplete = true)
    public HashMap<String, Boolean> checkPedigreeBook() {
        return generateBooleanHashMap("pedigreeBookOk", 97);
    }

    @ZeebeWorker(type = "checkPlate", autoComplete = true)
    public HashMap<String, Boolean> checkPlate() {
        return generateBooleanHashMap("plateOk", 90);
    }

    @ZeebeWorker(type = "checkDemolitionTakeover", autoComplete = true)
    public HashMap<String, Boolean> checkDemolitionTakeover() {
        return generateBooleanHashMap("demolitionTakeover", 90);
    }

    @ZeebeWorker(type = "checkRegDoc", autoComplete = true)
    public HashMap<String, Boolean> checkRegDoc() {
        return generateBooleanHashMap("regDocOk", 90);
    }

    @ZeebeWorker(type = "checkInsurance", autoComplete = true)
    public HashMap<String, Boolean> checkInsurance() {
        return generateBooleanHashMap("insuranceOk", 90);
    }

    @ZeebeWorker(type = "checkPropertyTax", autoComplete = true)
    public HashMap<String, Boolean> checkPropertyTax() {
        return generateBooleanHashMap("propertyTaxOk", 90);
    }

    @ZeebeWorker(type = "checkTechnicalInspection", autoComplete = true)
    public HashMap<String, Boolean> checkTechnicalInspection() {
        return generateBooleanHashMap("technicalInspectionOk", 90);
    }

    @ZeebeWorker(type = "sendResult", autoComplete = true)
    public void sendResult(ActivatedJob job) {
        Map<String, Object> variables = job.getVariablesAsMap();
        zeebeClient.newPublishMessageCommand().messageName("result").correlationKey("result").variables(variables).send().join();
    }

    @ZeebeWorker(type = "checkRegistrationPermit", autoComplete = true)
    public HashMap<String, Boolean> checkRegistrationPermit() {
        return generateBooleanHashMap("registrationPermitOk", 70);
    }

    @ZeebeWorker(type = "checkServiceFeePayed", autoComplete = true)
    public HashMap<String, Boolean> checkServiceFeePayed() {
        return generateBooleanHashMap("serviceFeePayedOk", 93);
    }

    @ZeebeWorker(type = "checkDataChangeConf", autoComplete = true)
    public HashMap<String, Boolean> checkDataChangeConf() {
        return generateBooleanHashMap("dataChangeConfOk", 81);
    }

    @ZeebeWorker(type = "checkMedicalFitness", autoComplete = true)
    public HashMap<String, Boolean> checkMedicalFitness() {
        return generateBooleanHashMap("medicalFitnessOk", 60);
    }

    @ZeebeWorker(type = "checkSalesContract", autoComplete = true)
    public HashMap<String, Boolean> checkSalesContract() {
        return generateBooleanHashMap("salesContractOk", 80);
    }

    @ZeebeWorker(type = "checkCompulsoryInsurance", autoComplete = true)
    public HashMap<String, Boolean> checkCompulsoryInsurance() {
        return generateBooleanHashMap("compulsoryInsuranceOk", 80);
    }

    @ZeebeWorker(type = "checkAuthenticity", autoComplete = true)
    public HashMap<String, Boolean> checkAuthenticity() {
        return generateBooleanHashMap("authenticityOk", 73);
    }

    @ZeebeWorker(type = "checkTransferCostPaid", autoComplete = true)
    public HashMap<String, Boolean> checkTransferCostPaid() {
        return generateBooleanHashMap("transferCostPaidOk", 98);
    }

    @ZeebeWorker(type = "checkForeignLicenceIssued", autoComplete = true)
    public HashMap<String, Boolean> checkForeignLicenceIssued() {
        return generateBooleanHashMap("foreignLicenceIssuedOk", 85);
    }

    @ZeebeWorker(type = "checkDrivingLicenceRegisterDoc", autoComplete = true)
    public HashMap<String, Boolean> checkDrivingLicenceRegisterDoc() {
        return generateBooleanHashMap("drivingLicenceRegisterDocOk", 90);
    }

    @ZeebeWorker(type = "checkNaturalistaionFee", autoComplete = true)
    public HashMap<String, Boolean> checkNaturalistaionFee() {
        return generateBooleanHashMap("naturalistaionFeeOk", 95);
    }

    @ZeebeWorker(type = "checkTranslation", autoComplete = true)
    public HashMap<String, Boolean> checkTranslation() {
        return generateBooleanHashMap("translationOk", 78);
    }

    @ZeebeWorker(type = "checkDutyPayment", autoComplete = true)
    public HashMap<String, Boolean> checkDutyPayment() {
        return generateBooleanHashMap("dutyPaymentOk", 98);
    }

    @ZeebeWorker(type = "checkPecuniaryValueRegistration", autoComplete = true)
    public HashMap<String, Boolean> checkPecuniaryValueRegistration() {
        return generateBooleanHashMap("pecuniaryValueRegistrationOk", 82);
    }

    @ZeebeWorker(type = "needSupplementarySubmission", autoComplete = true)
    public void needSupplementarySubmission() {
        zeebeClient.newPublishMessageCommand().messageName("needSupplementarySubmission").correlationKey("needSupplementarySubmission").send().join();
    }

    @ZeebeWorker(type = "isReplacementOk", autoComplete = true)
    public HashMap<String, Boolean> isReplacementOk() {
        HashMap<String, Boolean> variable = generateBooleanHashMap("isReplacementOk", 90);
        zeebeClient.newPublishMessageCommand().messageName("isReplacementOk").correlationKey("isReplacementOk").variables(variable).send().join();
        return variable;
    }

    @ZeebeWorker(type = "sendDataToAuthority", autoComplete = true)
    public void sendDataToAuthority() {
        zeebeClient.newPublishMessageCommand().messageName("sendDataToAuthority").correlationKey("sendDataToAuthority").send().join();
    }

    @ZeebeWorker(type = "rejectRequest", autoComplete = true)
    public void rejectRequest() {
        zeebeClient.newPublishMessageCommand().messageName("rejectRequest").correlationKey("rejectRequest").send().join();
    }

    @ZeebeWorker(type = "sendRegCertificate", autoComplete = true)
    public void sendRegCertificate() {
        zeebeClient.newPublishMessageCommand().messageName("sendRegCertificate").correlationKey("sendRegCertificate").send().join();
    }

    @ZeebeWorker(type = "validateResult", autoComplete = true)
    public HashMap<String, Boolean> validateResult(final ActivatedJob job) {
        Set<String> exceptions = Set.of("isAgainstLaw", "isReplacement", "isMisappropriated", "isDemolition");
        boolean allDocOk = true;
        Map<String, Object> variables = job.getVariablesAsMap();

        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            if (!exceptions.contains(entry.getKey()) && entry.getValue() instanceof Boolean) {
                if (!(Boolean) entry.getValue()) {
                    allDocOk = false;
                    break;
                }
            }
        }

        HashMap<String, Boolean> checkOk = new HashMap<>();
        checkOk.put("checkOk", allDocOk);

        return checkOk;
    }

    @ZeebeWorker(type = "sendConfirmation", autoComplete = true)
    public void sendConfirmation() {
        zeebeClient.newPublishMessageCommand().messageName("sendConfirmation").correlationKey("sendConfirmation").send().join();
    }

}