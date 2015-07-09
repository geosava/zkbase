# Reason #

Once a domain object is declared by use of the _@Entity_ annotation, JPA will use the name of the Java class as a table name in the data base. This is very convenient and sufficient in most applications. There are, however, issues with some data bases (e.g. Oracle), as there are some names declared as illegal table names. Thus, causing errors at run time.
For instance Oracle will not allow a table name called "USER".
As a workaround for the above mentioned issue as well as for indicating the zkbase-tables in a shared DB the prefix "zkb" was introduced, which is configured by adding the annotation: _@Table(name="zkb\_user")_ to each domain object.