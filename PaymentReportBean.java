package Entity;
import Bean.SegnalazionePagamentoBean;
import DAO.JDBCSegnalazionePagamento;
import java.sql.SQLException;
import java.io.Serializable;

public class PaymentReportBean implements Serializable {
    private long  paymentReportID;
    private Contract contractID;
    private String renterName;
    private Lessor lessorName;
    private int reportNumber;
    private String expirationReport;
    private int State;
    private int Notified;
    private JDBCSegnalazionePagamento jdbcSegnalazionePagamento;
}