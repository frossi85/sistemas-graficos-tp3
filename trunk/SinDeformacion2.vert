#version 110
#define PI    3.14159265
#define CANTIDAD_LUCES 1

varying vec3 normal;
varying vec3 eyeVec;

varying vec3 lightDir[CANTIDAD_LUCES];
varying float dist[CANTIDAD_LUCES];


void main()
{
   normal = gl_NormalMatrix * gl_Normal;
   
   vec3 aux;
   vec4 ecPos = gl_ModelViewMatrix * gl_Vertex;
   vec3 vVertex = vec3(gl_ModelViewMatrix * gl_Vertex);
   eyeVec = -vVertex;
    
   for(int i = 0; i < CANTIDAD_LUCES; i++)
   {
	   aux = vec3(gl_LightSource[i].position-ecPos);
	   lightDir[i] = normalize(aux);
	   dist[i] = length(aux);
   }

   gl_Position = ftransform();
}
