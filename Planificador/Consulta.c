//COMPILACIÓN:
//gcc -I/usr/include/mysql Consulta.c -lmysqlclient -o Consulta

/* librerías que usaremos */
#include <mysql.h> /* libreria que nos permite hacer el uso de las conexiones y consultas con MySQL */
#include <stdio.h> /* Para poder usar printf, etc. */

int main()
{
	MYSQL *conn; /* variable de conexión para MySQL */
	MYSQL_RES *res; /* variable que contendra el resultado de la consuta */
	MYSQL_ROW row; /* variable que contendra los campos por cada registro consultado */
	char *server = "localhost"; /*direccion del servidor 127.0.0.1, localhost o direccion ip */
	char *user = "root"; /*usuario para consultar la base de datos */
	char *password = "root"; /* contraseña para el usuario en cuestion */
	char *database = "tt"; /*nombre de la base de datos a consultar */
	conn = mysql_init(NULL); /*inicializacion a nula la conexión */

	/* conectar a la base de datos */
	if (!mysql_real_connect(conn, server, user, password, database, 0, NULL, 0))
	{ /* definir los parámetros de la conexión antes establecidos */
		fprintf(stderr, "%s\n", mysql_error(conn)); /* si hay un error definir cual fue dicho error */
		exit(1);
	}

	/* enviar consulta SQL */
	if (mysql_query(conn, "SELECT count(*)  FROM estacion WHERE id_estacion > 50"))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		exit(1);
	}

	res = mysql_use_result(conn);

	char numEstaciones = atoi(mysql_fetch_row(res)[0]) + 1;

	char nodoEstaciones[numEstaciones][numEstaciones];

	int i = 0, j = 0;
	for (i = 0; i < numEstaciones; i++)
	{
		for (j = 0; j < numEstaciones; j++)
		{
			nodoEstaciones[i][j] = 0;
		}
	}

	//printf("NumEstaciones = %d\n", numEstaciones - 1);

	mysql_free_result(res);

	/* enviar consulta SQL */
	if (mysql_query(conn, "SELECT * FROM ruta_estacion WHERE id_ruta >= 9;"))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		exit(1);
	}

	res = mysql_use_result(conn);

	while ((row = mysql_fetch_row(res)) != NULL){ /* recorrer la variable res con todos los registros obtenidos para su uso */
		char estacionOrigen = atoi(row[1]) - 50;
		char estacionDestino = atoi(row[2]) -50;

		if(estacionDestino > 0)
			nodoEstaciones[estacionOrigen][estacionDestino] = 1;

		if(atoi(row[3]) > 0){
			//¡Hace falta separar por comas!
			char estacionTransbordo = atoi(row[3]);
			nodoEstaciones[estacionOrigen][estacionTransbordo] = 1;
		}
	}
	/* se libera la variable res y se cierra la conexión */
	mysql_free_result(res);
	mysql_close(conn);
	
	//puts("NODO DE ESTACIONES");

	/*printf("strict graph rutas\n{\nI [penwidth=0];\nI [label=\"\"];\n");
	for(i = 1; i < numEstaciones; i++){
		printf("%d [shape=circle];\n", i);
	}
	for (i = 0; i < numEstaciones; i++)
	{
		for(j = 0; j < numEstaciones; j++){
			if(nodoEstaciones[i][j]){
				printf("%d -- %d [label=\"\"];\n", i, j);
			}
		}
	}

	puts("}");*/

	srand(time(NULL));

	printf("numEstaciones = %d\n", numEstaciones);

	int eOrigenUs = rand() % (numEstaciones);
	int eDestinoUs = 0;
	//Validación debido a que una ruta no tiene transbordes
	if(eOrigenUs >= 52 && eOrigenUs <= 68)
		do
		{
			eDestinoUs = rand() % 17 + 52;
		} while (eOrigenUs == eDestinoUs && eDestinoUs != 0);
	else{
		do
		{
			eDestinoUs = rand() % 52;
		} while (eOrigenUs == eDestinoUs && eDestinoUs != 0);
	}

	printf("El usuario quiere viajar de la estación %d a la estación %d\n", eOrigenUs, eDestinoUs);
}
