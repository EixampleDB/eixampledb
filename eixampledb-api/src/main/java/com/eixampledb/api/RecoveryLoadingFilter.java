package com.eixampledb.api;

import com.eixampledb.core.Snapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RecoveryLoadingFilter extends OncePerRequestFilter {
    private Snapshot snapshotFilter;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException{
        if(snapshotFilter.isLoadingServiceSnapshots || false/*Introduce boolean of BinaryLog*/){
            httpServletResponse.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }else{
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }
}