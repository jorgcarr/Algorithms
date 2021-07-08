import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Contiene la Funcionalidad de Autenticacion
 * y busquede de atributos de un usuario en el LDAP
 *
 * @author  John Daza
 * @since   1.5
 * @version 1.0
 */
public final class ldapAuth {

    private String usuario;
    private String clave;
    private String servidor;
    private String dn;
    private String tipoAuth;
    private String mensajeError;
    private boolean autenticado;

    DirContext dc;

    /**
     * Constructor de la conexion con el Motor de LDAP
     *
     * @param server  Servidor en donde se encuentra el LDAP
     * @param dn      Directoria del arbol del LDAP
     * @param ta      Tipo de Autenticacion
     * @param usuario Usuario que desea realizar la conexion
     * @param clave   Clave del usuario
     *
     */
    public ldapAuth(String server, String dn, String ta,String usuario,String clave) {
        this.servidor = server;
        this.dn = dn;
        this.tipoAuth = ta;
        this.usuario=usuario;
        this.clave=clave;
        inicializarConexion();
    }

    public void inicializarConexion() {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, servidor);
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(Context.SECURITY_AUTHENTICATION, tipoAuth);
        env.put(Context.SECURITY_PRINCIPAL, dn);
        env.put(Context.SECURITY_CREDENTIALS, clave);


        try {
            dc = new InitialDirContext(env);
            setAutenticado(true);
        } catch (NamingException ex) {

            System.out.println("Error Autenticando mediante LDAP, Error causado por : " + ex.toString());
            setAutenticado(false);
            setMensajeError(ex.toString());
        }
    }

    /**
     * Retorna el Atributo de la conexion con LDAP actual
     *
     * @param atributo Nombre del Atributo que se desea obtener
     * @return Attribute con la informacion correspondiente
     */

    public Attribute cargarPropiedadConexion(String atributo) {
        Attribute propiedad = null;

        try {
            Attributes attrs = dc.getAttributes(dn);

            if (attrs == null) {
                propiedad = null;
            } else {
                propiedad = attrs.get(atributo);
            }
        } catch (Exception e) {
            propiedad = null;
        }
        return propiedad;
    }


    /*Get's y Set's*/

    public void setMensajeError(String mensaje) {
        this.mensajeError = mensaje;
    }

    public String getMensajeError()
    {
        return mensajeError;
    }

    public boolean isAutenticado() {
        return autenticado;
    }
    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public static void main(String[] args) {
        String server = "COMPENSAR.CCF";
        String dn = "";
        String ta = "";
        String usuario = "";
        String clave = "";
        ldapAuth ldapAuth = new ldapAuth(server,dn, ta, usuario, clave);
    }
}