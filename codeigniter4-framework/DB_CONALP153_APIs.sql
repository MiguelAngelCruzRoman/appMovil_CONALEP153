create database DB_CONALEP153_APIs;
use DB_CONALEP153_APIs;

create table usuario(
	id_usuario int not null auto_increment primary key,
	primerNombre varchar(50) not null,
	segundoNombre varchar(50) null,
    apellidoPaterno varchar(50) not null,
	apellidoMaterno varchar(50) not null,
    username varchar(50) not null unique,
    password varchar(50) not null,
    foto text not null,
    rol enum("DOCENTE","ADMINISTRATIVO","ALUMNO") not null
);

create table actividad(
	id_actividad int not null auto_increment primary key,
    nombre varchar(50) not null,
    descripcion text not null,
    imagen text not null,
    fecha date not null,
    ubicacion text not null
);

create table actividad_usuario(
	id_actividadUsuario int not null auto_increment primary key,
    id_usuario int not null,
    id_actividad int not null,
    foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_actividad) references actividad(id_actividad)
);

create table mensaje_actividad(
	id_mensajeActividad int not null auto_increment primary key,
    id_actividad int not null,
    id_usuario int not null,
    fecha date not null,
    contenido text not null,
    foreign key (id_actividad) references actividad (id_actividad),
    foreign key (id_usuario) references usuario (id_usuario)
);

create table grupo(
	id_grupo int not null auto_increment primary key,
    nombre varchar(50) not null,
    imagen text not null
);

create table mensaje_grupo(
	id_mensajeGrupo int not null auto_increment primary key,
    id_grupo int not null,
    id_usuario int not null,
    fecha date not null,
    contenido text not null,
    foreign key (id_grupo) references grupo (id_grupo),
    foreign key (id_usuario) references usuario (id_usuario)
);

create table mensaje_docente(
	id_mensajeDocente int not null auto_increment primary key,
    id_docente int not null,
    id_alumno int not null,
    fecha date not null,
    contenido text not null,
    foreign key (id_docente) references usuario (id_usuario),
    foreign key (id_alumno) references usuario (id_usuario)
);

