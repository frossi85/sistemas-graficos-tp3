varying vec3 normal, lightDir, eyeVec, spotDir;

const float cos_outer_cone_angle = 3.15;//0.8; // 36 degrees

void main (void)
{
	vec4 final_color =
	(gl_FrontLightModelProduct.sceneColor * gl_FrontMaterial.ambient) +
	(gl_LightSource[0].ambient * gl_FrontMaterial.ambient);

	vec3 L = normalize(lightDir);
	vec3 D = normalize(-gl_LightSource[0].spotDirection);
	//vec3 D = normalize(-spotDir);

	float cos_cur_angle = dot(-L, D);

	float cos_inner_cone_angle = gl_LightSource[0].spotCosCutoff;

	float cos_inner_minus_outer_angle = 
	      cos_inner_cone_angle - cos_outer_cone_angle;
	
	//****************************************************
	// Don't need dynamic branching at all, precompute 
	// falloff(i will call it spot)
	float spot = 0.0;
	spot = clamp((cos_cur_angle - cos_outer_cone_angle) / 
	       cos_inner_minus_outer_angle, 0.0, 1.0);
	//****************************************************

	//spot = 1.32;

	vec3 N = normalize(normal);

	float lambertTerm = max( dot(N,L), 0.0);
	//if(lambertTerm > 0.0)
	//{
		final_color += gl_LightSource[0].diffuse *
			gl_FrontMaterial.diffuse *
			lambertTerm * spot;

		vec3 E = normalize(eyeVec);
		//vec3 R = reflect(-L, N);
		vec3 R = reflect(-L, N);

		float specular = pow( max(dot(R, E), 0.0),
			gl_FrontMaterial.shininess );
			

		final_color += gl_LightSource[0].specular *
			gl_FrontMaterial.specular *
			specular * spot;
	//}
	gl_FragColor = final_color;
}