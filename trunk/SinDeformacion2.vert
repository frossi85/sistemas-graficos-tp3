#version 110
#define PI    3.14159265

varying vec3 normal;
varying vec3 lightDir;
varying float Dist;

varying vec3 eyeVec, spotDir;


void main()
{
   normal = gl_NormalMatrix * gl_Normal;
   
   vec4 ecPos = gl_ModelViewMatrix * gl_Vertex;
   vec3 aux = vec3(gl_LightSource[0].position-ecPos);
    
   lightDir = normalize(aux);
   Dist= length(aux);
   
   
   vec3 vVertex = vec3(gl_ModelViewMatrix * gl_Vertex);
   eyeVec = -vVertex;

   gl_Position = ftransform();
}

/*
void main()
{	
	normal = gl_NormalMatrix * gl_Normal;

	vec3 vVertex = vec3(gl_ModelViewMatrix * gl_Vertex);

	lightDir = vec3(gl_LightSource[0].position.xyz - vVertex);
	spotDir = vec3(gl_LightSource[0].spotDirection - vVertex);
	
	eyeVec = -vVertex;

	gl_Position = ftransform();		
}*/