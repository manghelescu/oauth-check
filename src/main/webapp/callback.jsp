<%@ page import="java.util.Enumeration" %>
<html>
<head>
<title>User Info</title>
</head>
<body>
<h1>JSP Request Headers</h1>
Request URI = <code><%= request.getRequestURI () %></code>
<p>
<%
    Enumeration headers = request.getHeaderNames ();
    if (headers == null){
    	out.println("No headers");
    	return;
    }

    	String headername;
        String headerval;
%>
        <ul>
<%
        while (headers.hasMoreElements ())
          {
            headername = (String) headers.nextElement ();
            headerval = request.getHeader (headername);
%>
            <li><code><%= headername %></code> =
<%
            if (headerval == null)
              {
%>
                &lt;null&gt;
<%
              }
            else
              {
%>
                <code><%= headerval %></code>
<%
              }
          }
%>
        </ul>

<br/>

<%
    Enumeration parameterNames = request.getParameterNames();
    if (parameterNames == null){
    	out.println("No parameters");
    	return;
    }

    	String paramName;
        String paramVal;
%>
        <ul>
<%
        while (parameterNames.hasMoreElements ())
          {
        	paramName = (String) parameterNames.nextElement ();
        	paramVal = request.getParameter (paramName);
%>
            <li><code><%= paramName %></code> =
<%
            if (paramVal == null)
              {
%>
                &lt;null&gt;
<%
              }
            else
              {
%>
                <code><%= paramVal %></code>
<%
              }
          }
%>
        </ul>


</body>
</html>