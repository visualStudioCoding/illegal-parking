function appendObjTo(thatArray, newObj) {
    const frozenObj = Object.freeze(newObj);
    return Object.freeze(thatArray.concat(frozenObj));
}

/******************************
 * 폴리곤 내 포함 여부 체크 start
 * ****************************/
let INF = 10000;

class Point {
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
}

function onSegment(p, q, r) {
    return q.x <= Math.max(p.x, r.x) &&
        q.x >= Math.min(p.x, r.x) &&
        q.y <= Math.max(p.y, r.y) &&
        q.y >= Math.min(p.y, r.y);

}

function orientation(p, q, r) {
    let val = (q.y - p.y) * (r.x - q.x)
        - (q.x - p.x) * (r.y - q.y);

    if (val === 0) {
        return 0; // collinear
    }
    return (val > 0) ? 1 : 2; // clock or counterclock wise
}

function doIntersect(p1, q1, p2, q2) {
    let o1 = orientation(p1, q1, p2);
    let o2 = orientation(p1, q1, q2);
    let o3 = orientation(p2, q2, p1);
    let o4 = orientation(p2, q2, q1);

    // General case
    if (o1 !== o2 && o3 !== o4) {
        return true;
    }

    // Special Cases
    // p1, q1 and p2 are collinear and
    // p2 lies on segment p1q1
    if (o1 === 0 && onSegment(p1, p2, q1)) {
        return true;
    }

    // p1, q1 and p2 are collinear and
    // q2 lies on segment p1q1
    if (o2 === 0 && onSegment(p1, q2, q1)) {
        return true;
    }

    // p2, q2 and p1 are collinear and
    // p1 lies on segment p2q2
    if (o3 === 0 && onSegment(p2, p1, q2)) {
        return true;
    }

    // p2, q2 and q1 are collinear and
    // q1 lies on segment p2q2
    if (o4 === 0 && onSegment(p2, q1, q2)) {
        return true;
    }

    // Doesn't fall in any of the above cases
    return false;
}


function isInside(polygon, n, p) {
    // There must be at least 3 vertices in polygon[]
    if (n < 3) {
        return false;
    }

    // Create a point for line segment from p to infinite
    let extreme = new Point(INF, p.y);

    // Count intersections of the above line
    // with sides of polygon
    let count = 0, i = 0;
    do {
        let next = (i + 1) % n;

        // Check if the line segment from 'p' to
        // 'extreme' intersects with the line
        // segment from 'polygon[i]' to 'polygon[next]'
        if (doIntersect(polygon[i], polygon[next], p, extreme)) {
            // If the point 'p' is collinear with line
            // segment 'i-next', then check if it lies
            // on segment. If it lies, return true, otherwise false
            if (orientation(polygon[i], p, polygon[next]) === 0) {
                return onSegment(polygon[i], p,
                    polygon[next]);
            }

            count++;
        }
        i = next;
    } while (i !== 0);

    // Return true if count is odd, false otherwise
    return (count % 2 === 1); // Same as (count%2 == 1)
}
/*폴리곤 내 포함 여부 체크 end*/