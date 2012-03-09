varying vec3 normal, lightDir, eyeVec, spotDir;

void main()
{	
	normal = gl_NormalMatrix * gl_Normal;

	vec3 vVertex = vec3(gl_ModelViewMatrix * gl_Vertex);

	lightDir = vec3(gl_LightSource[0].position.xyz - vVertex);
	spotDir = vec3(gl_LightSource[0].spotDirection - vVertex);
	
	eyeVec = -vVertex;

	gl_Position = ftransform();		
}