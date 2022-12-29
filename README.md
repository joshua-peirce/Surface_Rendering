# Surface_Rendering
Original idea for how to render a 3D surface using linear algebra. Implemented in Java using Swing.

The idea of this project came to me as I was taking my linear algebra class. The idea is taking all the vectors in the surface and projecting them into a
plane, and then creating some basis for that plane using the camera's angle. Each point was then expressed as a combination of these basis vectors, which
directly corresponded to a location on the screen. As I am rendering surfaces, these vertices have lines drawn through them.
