import pygame
from cmath import sqrt, tau

pygame.init()

#========EDIT========
screenSize = (1080, 720)
backgroundColour = (255, 255, 255)
circleColour = (0, 50, 100)
deepColour = (255, 50, 100)
backGroundColour = (255, 255, 255)
scaleFactor = 1/300
gasketDepth = 2
#=======/EDIT========

class Circle:
    def __init__(self, center, radius, colour):
        self.center = center
        self.radius = abs(radius)
        self.curvature = 1/radius
        self.colour = colour

    def draw(self, surface):
        pygame.draw.circle(
            surface,
            self.colour,
            (
                round(self.center.real),
                round(self.center.imag)
            ),
            round(self.radius),
            min(int(self.radius), 1)
        )

def getOuterCircle(c1, c2, c3):
    curv1 = c1.curvature
    curv2 = c2.curvature
    curv3 = c3.curvature
    m1 = c1.center
    m2 = c2.center
    m3 = c3.center
    curvO = -2 * sqrt(
            curv1*curv2 + curv2*curv3 + curv1*curv3
        ) + curv1 + curv2 + curv3
    mO = ( -2 * sqrt( curv1*m1*curv2*m2 + curv2*m2*curv3*m3 + curv1*m1*curv3*m3 ) + curv1*m1 + curv2*m2 + curv3*m3 ) /  curvO

    return Circle( mO, (1/curvO).real, colour )

def getInnerCircle(c1, c2, c3, colour):
    curv1 = c1.curvature
    curv2 = c2.curvature
    curv3 = c3.curvature
    m1 = c1.center
    m2 = c2.center
    m3 = c3.center
    curvO = -2 * sqrt(
            curv1*curv2 + curv2*curv3 + curv1*curv3
        ) + curv1 + curv2 + curv3
    mO = ( -2 * sqrt( curv1*m1*curv2*m2 + curv2*m2*curv3*m3 + curv1*m1*curv3*m3 ) + curv1*m1 + curv2*m2 + curv3*m3 ) /  curvO

    curvI = 2 * (curv1 + curv2 + curv3) - curvO
    mI = (2 * (curv1*m1 + curv2*m2 + curv3*m3) - curvO*mO ) / curvI
    return Circle( mI, (1/curvI).real, colour )

size = min(screenSize[0], screenSize[1]*2/sqrt(3).real)

screen = pygame.display.set_mode( screenSize )
pygame.display.set_caption("Apollonian")

surface = pygame.Surface(screen.get_size())
surface = surface.convert()
surface.fill(backgroundColour)

circle1 = Circle(
    complex(
        screenSize[0]/2 - size,
        screenSize[1]/2 - size*sqrt(3)/4
    ),
    size,
    circleColour
)

circle2 = Circle(
    complex(
        screenSize[0]/2 + size,
        screenSize[1]/2 - size*sqrt(3)/4
    ),
    size,
    circleColour
)

circle3 = Circle(
    complex(
        screenSize[0]/2,
        screenSize[1]/2 + size*3*sqrt(3)/4
    ),
    size,
    circleColour
)

circles = [circle1, circle2, circle3]

def getColour(depth):
    r1 = depth / gasketDepth
    r2 = 1 - r1
    return (
        int(circleColour[0]*r1 + deepColour[0]*r2),
        int(circleColour[1]*r1 + deepColour[1]*r2),
        int(circleColour[2]*r1 + deepColour[2]*r2)
    )

def createGasket(c1, c2, c3, depth):
    if depth == 0:
        return

    c4 = getInnerCircle(c1, c2, c3, getColour(depth))
    circles.append(c4)
    createGasket(c1, c2, c4, depth - 1)
    createGasket(c2, c3, c4, depth - 1)
    createGasket(c3, c1, c4, depth - 1)

createGasket(circle1, circle2, circle3, gasketDepth)

for jCircle in circles:
    jCircle.draw(surface)

with open("competition.xml", "w+") as f:
    f.write(
    f'<scene>\n    <ambient-light colour="#+"/>\n    <point-light x="1" y="3" z="2" colour="#B3DDFF" intensity="80"/>\n    <point-light x="-5" y="1" z="4" colour="#FFB0B2" intensity="80"/>')

    for jCircle in circles:
        hexColour = ('#%02x%02x%02x' % jCircle.colour).upper()
        c = (jCircle.center - complex(screenSize[0]/2, screenSize[1]/2)) * scaleFactor
        r = jCircle.radius * scaleFactor
        f.write(f'    <sphere x="{c.real}" y="{c.imag}" z="3.5" radius="{r}" colour="{hexColour}"/>\n')

    f.write("</scene>\n")

screen.blit(surface, (0, 0))
pygame.display.flip()

"""
clock = pygame.time.Clock()

shouldExit = False

while not shouldExit:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            shouldExit = True

    clock.tick(60)
"""
pygame.quit()
