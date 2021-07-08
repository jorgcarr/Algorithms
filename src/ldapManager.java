import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.swing.JButton;

/**
 *LDAP
 * @author John Daza
 * @since   1.5
 * @version 1.0
 */

public class ldapManager{


    public static final String newline = "\n";
    public static final String GET_CONECTAR = "Conectar";

    public static final String EXIT = "Exit";


    public ldapManager() {
    }

    public static void main(String[] args) {


        ldapManager ldapManager = new ldapManager();


        try

        {

            // Especificar datos cuenta de Servicio
            //String ctaService = "SVCQAPORTALFI";
            //String pwdCtaService = "Svcp0rt4l2020#";

            String ctaService = "SVCQAPORTALFI";
            String pwdCtaService = "Svcp0rt4l2020#";
            
            String ldapSearchBase= "cn="+ ctaService +", ou=sa, o=compensar"; // Ruta del Arbol LDAP para autenticar cuentas de servicio

            // Especificar datos de conexion
            String tipoAth="simple";//tipo de autentuicacion simple o por SSL
            String server= "ldaps://ldap.compensar.com:636";
            String usuario= "jacaror";
            String clave = "Abril2021.";


            //Crear contexto con la cuenta de servicio

            Hashtable <String, Object> env = new Hashtable <String, Object> ();
            env.put (Context.SECURITY_AUTHENTICATION, "simple");
            if (ctaService != null)
            {
                env.put (Context.SECURITY_PRINCIPAL, ldapSearchBase);
            }
            if (pwdCtaService != null) {
                env.put (Context.SECURITY_CREDENTIALS, pwdCtaService);
            }
            env.put (Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put (Context.PROVIDER_URL, server);
            env.put(Context.SECURITY_PROTOCOL, "ssl");



            try

            {

                LdapContext dc = new InitialLdapContext(env, null); //Ejemplifica e  inicializa el contexto
                System.out.println( "SI se pudo  autenticar con cuenta de servicio , Error causado por : ");// imprime log de control
                SearchResult srLdapUser =  ldapManager.BuscarUsuarioPorLogin(dc,  usuario); // ) autentica la cuenta de servicio
                String userldap = srLdapUser.getNameInNamespace();  // obtenemos  Propiedad de la autenticacion
                String cedula = (userldap.split(",")[0]).replace("cn=",""); // se extrae el numero de cedula
                String ldapSearchUser= "cn="+ cedula +",ou=internos,ou=usuarios,o=compensar";//Se define el dn para autenticacion del usuario de RED a partir del numero de cedula
                ldapAuth objLdapAuth=new ldapAuth(server,ldapSearchUser,tipoAth,usuario,clave); //Ejemplifica e inicializa la conexion para el usuario de RED

                if(objLdapAuth.isAutenticado()){


                    ldapManager.setResultado("Usuario "+objLdapAuth.getUsuario()+" Autenticado Correctamente");
                    System.out.println("Usuario "+objLdapAuth.getUsuario()+" Autenticado Correctamente");


                }
                else{
                    ldapManager.setResultado("Usuario "+objLdapAuth.getUsuario()+ "No se pudo  Autenticar "); // + ldapAuth.getMensajeError());
                }

            }
            catch (Exception e2) {
                ldapManager.setResultado( "No se pudo autenticar con cuenta de servicio  Error Autenticando mediante LDAP, Error causado por : " + e2.toString());
                System.out.println( "No se pudo  autenticar con cuenta de servicio Error Autenticando mediante LDAP, Error causado por : " + e2.toString());
            }



        }
        catch (Exception ex) {
            ldapManager.setResultado( "No se pudo  Autenticar Error Autenticando mediante LDAP, Error causado por : " + ex.toString());
            System.out.println( "No se pudo  Autenticar Error Autenticando mediante LDAP, Error causado por : " + ex.toString());

        }

    }


    public SearchResult BuscarUsuarioPorLogin(DirContext ctx,  String usuario) throws NamingException {

        SearchControls searchControls = new SearchControls ();
        searchControls.setSearchScope (SearchControls.SUBTREE_SCOPE);

        //se realiza la busqueda del usuario filtrando por el atributo cCFUserAppDomainUser
        NamingEnumeration <SearchResult> resultados = ctx.search ("ou=internos,ou=usuarios,o=compensar","(cCFUserAppDomainUser=" + usuario +")" , searchControls);
        SearchResult searchResult = null;

        if (resultados.hasMoreElements ()) {
            searchResult = (SearchResult) resultados.nextElement ();
            // imprime log de control
            System.out.println ("SI se encontro el usuario con el accountName:" + usuario);

            // asegúrese de que no haya otro artículo disponible, solo debe haber 1 coincidencia
            if (resultados.hasMoreElements ()) {
                System.err.println ("Coincidió con varios usuarios para el accountName:" + usuario);
                return null;
            }
        }

        else
        {

            System.out.println ("NO se encontro el usuario con el accountName:" + usuario);
            setResultado( "NO se encontro el usuario con el accountName:" + usuario);

        }
        return searchResult;
    }

    public static void setResultado(String r) {
        System.out.println(r);
    }


}


