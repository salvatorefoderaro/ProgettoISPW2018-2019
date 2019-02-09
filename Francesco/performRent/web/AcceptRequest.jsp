<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.ContractTextBean" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException" %>
<%@ page import="it.uniroma2.ispw.fersa.userProfileAndServices.NicknameNotFoundException" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionBean"></jsp:useBean>

<%
    ContractTextBean contractTextBean;
    if (!sessionBean.isLogged()) {
        session.setAttribute("warningMessage", "Non sei loggato");
        response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        return;
    } else if(request.getParameter("signature") != null) {

        System.out.println("Starting signature");

        try {
            sessionBean.getControl().signContract();

        } catch (SQLException | ClassNotFoundException | ConfigException | ConfigFileException | ContractPeriodException e) {
            session.setAttribute("warningMessage", e.toString());
            response.sendRedirect(response.encodeRedirectURL("index.jsp"));
            return;
        }

        session.setAttribute("warningMessage", "Contratto firmato correttamente!");
        response.sendRedirect(response.encodeRedirectURL("RenterPage.jsp"));
        return;
    } else {

        try {
            sessionBean.getControl().createContract();
            contractTextBean = sessionBean.getControl().getContractByRequest();
        } catch (SQLException | ClassNotFoundException | ConfigException | ConfigFileException | NicknameNotFoundException e) {
            session.setAttribute("warningMessage", e.toString());
            response.sendRedirect(response.encodeRedirectURL("index.jsp"));
            return;
        }
    }
%>


<html>
<head>
    <title>Firma del contratto</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="libs/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <h2 class="font-weight-bold text-center"><%=contractTextBean.getContracTypeName()%></h2>
        <p class="text-justify">Il/la Sig./Sig.ra <%=contractTextBean.getRenterSurname() + " " +
                contractTextBean.getRenterName()%> denominato/a locatore concede in locazione
        al Sig./Sig.ra <%=contractTextBean.getTenantSurname() + " " + contractTextBean.getTenantName()%>, nato/a a
            <%=contractTextBean.getTenantCityOfBirth()%> e residente a
            <%=contractTextBean.getTenantAddress()%> C.F. <%=contractTextBean.getTenantCF()%> di seguito denominato/a conduttore, che
        accetta di per sè, una porzione dell'unità immobiliare posta in <%=contractTextBean.getAptAddress()%>. Il locatore
        dichiara che gli impianti sono a normza con le vigenti normative in materia.</p>
        <p class="text-justify">La locazione è regolata da:</p>
        <p class="font-weight-bold text-center">Durata</p>
        <p class="text-justify">Il contratto è stipulato per la durata di <%=contractTextBean.getNumMonths()%> mesi, a decorrere
            dal <%=contractTextBean.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%> e fino al
            <%=contractTextBean.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%>.</p>
        <%
            if (contractTextBean.isTransitory()) {
        %>
        <p class="font-weight-bold text-center">Natura transitoria</p>
        <p class="text-justify">Le parti concordano che la presente locazione ha natura transitoria in quanto il
        conduttore espressamente ha l'esigenza di abitare l'immobile per motivi di studio o lavoro.</p>
        <%
            }
        %>
        <p class="font-weight-bold text-center">Canone</p>
        <p class="text-justify">Il canone di locazione è convenuto complessivamente in euro <%=contractTextBean.getTotalPrice()%> che il
            conduttore si obbliga a corrispondere, rispettivamente, in  <%=contractTextBean.getNumMonths()%> rate eguali mensili
            entro i primi di ogni mese, ciascuna di euro <%=(int) contractTextBean.getTotalPrice() / contractTextBean.getNumMonths()%>.</p>
        <p class="font-weight-bold text-center">Deposito cauzionale</p>
        <p class="text-justify">A garanzia delle obbligazioni assunte col presente contratto, il conduttore versa al
            locatore (che con la firma del contratto ne rilascia, in caso, quietanza) una somma di euro
            <%=contractTextBean.getDeposit()%>.</p>
        <p class="font-weight-bold text-center">Cedolare secca</p>
        <p class="text-justify">Il locatore dichiara di aderire alla cedolare secca.</p>
        <p class="font-weight-bold text-center">Pagamento, risoluzione e prelazione</p>
        <p class="text-justify">Le obbligazioni di pagamento scaturenti dal presente contratto costituiscono obbligazioni
            parziarie e divisibili ai sensi dell'art. 1314 C.C. e ciascuno dei debitori non è tenuto a pagare il debito
            che per la sua parle.
            Le spese sono escluse dal canone di locazione mensilmente corrisposto.
            11 pagamento del canone o di quant'altro dovuto anche per oneri accessori non può venire sospeso o
            ritardato da pretese o eccezioni del conduttore, quale ne sia il titolo. Il mancato puntuale pagamento,
            per qualsiasi causa, anche di una sola rata del canone (nonché di quant'altro dovuto, ove di imporlo
            pari almeno ad una mensilità del canone), costituisce in mora il conduttore inadempiente, fatto salvo
            quanto previsto dall'articolo 55 della Legge n. 392/78. La vendita dell'unità immobiliare locata - in
            relazione alla quale non viene concessa la prelazione al conduttore - non costituisce motivo di
            risoluzione del contratto.</p>
        <p class="text-center font-weight-bold">Uso</p>
        <p class="text-justify">L'immobile deve ssere destinato esclusivamente ad uso di civile abitazione dei
            conduttori . È fatto divieto di sublocare o dare in comodato, int tutto o in parte, l'unità immobiliare,
        pena la risoluzione i diritto del contratto. Il conduttore dovrà riconsegnare l ímmobile pulito ed integro,
        in ogni sua componente anche mobiliare; nel caso in cui si dovessero riscontrare es. muri sporchi, mobili,
        oggetti rotti o quant'altro, il conduttore si impegna a ripristinare il tutto a proprie spese.</p>
        <p class="font-weight-bold text-center">Varie</p>
        <p class="text-justify">Per quanto non previsto dal presente contratto le parti rinviano a quanto disposto in
            materia dal Codice Civile, dalle Leggi n. 392/78 e n. 431/98 o ccomunque dalle normi vigenti,
        dagli usi locali e dagli Accordi Territoriali.</p>
        <center>
        <form class="align-content-center" action="AcceptRequest.jsp" name="sign" method="get">
            <button type="submit" id="signature" name="signature" class="btn btn-primary btn-lg align-content-center">Firma</button>
        </form>
        </center>
    </div>
</body>
</html>
