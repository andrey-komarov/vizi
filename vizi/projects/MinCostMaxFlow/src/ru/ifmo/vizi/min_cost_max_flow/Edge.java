/*
*
* Edge class for MinCost-MaxFlow visualizer
*
* Copyright (C) 2008 by Aleksey Sergushichev <alsergbox@gmail.com>
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or (at
* your option) any later version.
*
* This program is distributed in the hope that it will be useful, but
* WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
* USA
*
*/

package ru.ifmo.vizi.min_cost_max_flow;

/**
 *
 * @author assaron
 */
public class Edge {

    public int u;
    public int v;
    public long capacity;
    public long cost;
    public long flow;
    public boolean reversed;
    public boolean highlighted;
    /*
    public Edge next;
    public Edge reverse;
     */

    public Edge(int u, int v, long capacity, long cost, long flow, boolean reversed) {
        this.u = u;
        this.v = v;
        this.capacity = capacity;
        this.cost = cost;
        this.flow = flow;
        this.reversed = reversed;
        this.highlighted = false;
        /*
        this.next = next;
        this.reverse = reverse;
         * */
    }
}

